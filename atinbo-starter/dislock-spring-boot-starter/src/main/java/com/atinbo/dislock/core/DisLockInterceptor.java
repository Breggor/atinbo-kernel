package com.atinbo.dislock.core;

import com.atinbo.dislock.annotation.DisLock;
import com.atinbo.dislock.annotation.LockKey;
import com.atinbo.dislock.core.strategy.*;
import com.atinbo.dislock.service.LockService;
import com.atinbo.dislock.service.LockServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 锁拦截器
 *
 * @author breggor
 */
@Slf4j
@Aspect
public class DisLockInterceptor {

    @Autowired
    private LockServiceFactory lockServiceFactory;

    private ThreadLocal<LockService> localLockService = new ThreadLocal<>();

    @Around(value = "@annotation(com.atinbo.dislock.annotation.DisLock)")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("[分布式锁] - 拦截进入处理>>>>>>>>>>>>>>");
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), targetMethod.getParameterTypes());

        DisLock lock = realMethod.getAnnotation(DisLock.class);
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = methodSignature.getName();

        if (log.isDebugEnabled()) {
            log.debug("[分布式锁] - 被锁定对象信息: class={}, method={}", className, methodName);
        }

        KeyStrategy keyStrategy = getKeyStrategy(className, methodName, realMethod, args);
        if (log.isDebugEnabled()) {
            log.debug("[分布式锁] - key生成策略: keyStrategy={}", keyStrategy);
        }

        KeyInfo lockKey = keyStrategy.generateBuilder().leaseTime(lock.leaseTime()).waitTime(lock.waitTime()).timeUnit(lock.timeUnit()).build();
        if (log.isDebugEnabled()) {
            log.debug("[分布式锁] - 锁key生成: lockKey={}", lockKey);
        }

        LockService lockService = lockServiceFactory.getService(lock.lockType());
        localLockService.set(lockService);
        lockService.setKeyInfo(lockKey);
        lockService.lock();
        return joinPoint.proceed();
    }

    private KeyStrategy getKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
        //参数锁
        for (int i = 0; i < realMethod.getParameters().length; i++) {
            if (realMethod.getParameters()[i].isAnnotationPresent(LockKey.class)) {
                return new ParameterKeyStrategy(className, methodName, realMethod, args);
            }
        }
        //方法锁
        if (null != realMethod.getAnnotation(LockKey.class)) {
            return new MethodKeyStrategy(className, methodName, realMethod, args);
        }
        //属性锁
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            @SuppressWarnings("rawtypes")
            Class objectClass = obj.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                if (null != field.getAnnotation(LockKey.class)) {
                    return new PropertiesKeyStrategy(className, methodName, realMethod, args);
                }
            }
        }
        //类名和方法名作为key的锁
        return new ClassKeyStrategy(className, methodName, realMethod, args);
    }

    @AfterReturning(value = "@annotation(com.atinbo.dislock.annotation.DisLock)")
    public void afterReturning(JoinPoint joinPoint) {
        localLockService.get().release();
        localLockService.remove();
    }

    @AfterThrowing(value = "@annotation(com.atinbo.dislock.annotation.DisLock)")
    public void afterThrowing(JoinPoint joinPoint) {
        localLockService.get().release();
        localLockService.remove();
    }
}