package com.atinbo.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpUtil;
import com.atinbo.core.spring.SpringContextHolder;
import com.atinbo.core.utils.IpUtil;
import com.atinbo.log.annotation.SysLog;
import com.atinbo.log.event.SysLogEvent;
import com.atinbo.log.model.SysLogSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zenghao
 * @date 2019-7-23
 * AOP操作日志
 */
@Aspect
@Slf4j
public class SysLogAspect {

    private static final String HEADER_CLIENT_ID = "X-GW-CLIENT-ID";
    private static final String HEADER_USER = "X-GW-USER";

    @Value("${spring.application.name}")
    private String module;

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.info("[模块]:{},[类名]:{},[方法]:{},[日志]:{}", module, strClassName, strMethodName, sysLog.value());

        SysLogSource logSource = getSystemLog();
        logSource.setTitle(sysLog.value());
        logSource.setType(sysLog.type());
        // 发送异步日志事件

        Long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            logSource.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            logSource.setTime(endTime - startTime);
            log.info("[类名]:{},[方法]:{},[耗时]:{}", strClassName, strMethodName, endTime - startTime);
            SpringContextHolder.publishEvent(new SysLogEvent(logSource));
        }
        return obj;
    }

    private SysLogSource getSystemLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLogSource logSource = new SysLogSource();
        logSource.setCreateBy(Objects.requireNonNull(request.getHeader(HEADER_USER)));
        logSource.setCreateTime(LocalDateTime.now());
        logSource.setRemoteAddr(IpUtil.getIpAddr(request));
        logSource.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        logSource.setMethod(request.getMethod());

        logSource.setUserAgent(request.getHeader(Header.USER_AGENT.toString()));
        logSource.setParams(HttpUtil.toParams(request.getParameterMap()));
        logSource.setServiceId(request.getHeader(HEADER_CLIENT_ID));
        logSource.setDelFlag(SysLogSource.DEL_FALSE);
        return logSource;
    }

}
