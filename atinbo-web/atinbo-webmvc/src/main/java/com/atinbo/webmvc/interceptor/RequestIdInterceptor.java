package com.atinbo.webmvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;


/**
 * 请求参数拦截
 *
 * @author breggor
 */
@Slf4j
@Component
public class RequestIdInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID = "requestId" ;
    ThreadLocal<Long> actTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        actTime.set(System.currentTimeMillis());

        String requestId = request.getHeader(REQUEST_ID);
        log.info("Header: requestId({})" , requestId);

        if (!StringUtils.isEmpty(requestId)) {
            requestId = request.getParameter(REQUEST_ID);
            log.info("Body: requestId({})" , requestId);
        }

        if (StringUtils.isEmpty(requestId)) {
            requestId = System.currentTimeMillis() + "" ;
        }

        MDC.put(REQUEST_ID, requestId);
        log.info(MessageFormat.format("url={0}, method={0}, queryString={0}" , request.getRequestURL().toString(), request.getMethod().toLowerCase(), request.getQueryString()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long consumeTime = System.currentTimeMillis() - actTime.get();
        log.warn("[" + request.getRequestURI() + "] consume time:" + consumeTime);
        if (ex != null) {
            log.error(ex.getMessage(), ex);
        }
        MDC.clear();
        actTime.remove();
    }
}