package com.atinbo.support.core.framework.aop;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zenghao 2017-03-24
 * service服务日志打印，需在application-context.xml文件添加以下配置。注：pom文件需要引入spring-aop和gson
 * <p>
 * <aop:aspectj-autoproxy proxy-target-class="true"/>
 * <bean class="com.clr.order.aop.LogAspect" />
 */
@Aspect
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger("function");

    /**
     * 方法开始，打印方法所有参数
     *
     * @param point
     */
    @Before("execution(* com.clr.*.service..*.*(..))")
    public void before(JoinPoint point) {
        logger.info("function：{}.{} param：{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), getArgs(point.getArgs()));
    }

    /**
     * 方法结束，打印方法返回值
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "execution(* com.clr.*.service..*.*(..))", returning = "returnValue")
    public void afterReturn(JoinPoint point, Object returnValue) {
        logger.info("function：{}.{} return：{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), getArgs(returnValue));
    }

    /**
     * 方法出现异常，打印异常信息
     *
     * @param point
     * @param exceptionValue
     */
    @AfterThrowing(pointcut = "execution(* com.clr.*.service..*.*(..))", throwing = "exceptionValue")
    public void afterException(JoinPoint point, Throwable exceptionValue) {
        logger.error(String.format("function：%s.%s throw：", point.getSignature().getDeclaringTypeName(), point.getSignature().getName()), exceptionValue);
    }

    private String getArgs(Object... args) {
        String result = "";
        if (ArrayUtils.isNotEmpty(args)) {
            try {
                result = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(args);
            } catch (Exception e) {
                logger.warn("args toJson error:", e);
                result = StringUtils.join(args, ",");
            }
        }
        return result;
    }
}
