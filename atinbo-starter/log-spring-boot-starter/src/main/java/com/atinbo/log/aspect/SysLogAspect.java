package com.atinbo.log.aspect;

import com.atinbo.core.spring.SpringContextHolder;
import com.atinbo.log.annotation.SysLog;
import com.atinbo.log.event.SysLogEvent;
import com.atinbo.log.model.SysLogSource;
import com.atinbo.log.util.SystemLogUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zenghao
 * @date 2019-7-23
 * AOP操作日志
 */
@Aspect
@Slf4j
public class SysLogAspect {

    @Value("${spring.application.name}")
    private String module;

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.info("[模块]:{},[类名]:{},[方法]:{},[日志]:{}", module, strClassName, strMethodName, sysLog.value());

        SysLogSource logVo = SystemLogUtils.getSystemLog();
        logVo.setTitle(sysLog.value());
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj = point.proceed();
        Long endTime = System.currentTimeMillis();
        logVo.setTime(endTime - startTime);
        log.info("[类名]:{},[方法]:{},[耗时]:{}", strClassName, strMethodName, endTime - startTime);
        SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        return obj;
    }

}
