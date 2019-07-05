package com.atinbo.mvc.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;


public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID = "requestId";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Long start = null;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long t = System.currentTimeMillis() - start;
        if (t > 200) {
            logger.warn("[" + request.getRequestURI() + "] consume time:" + t);
        } else {
            logger.warn("[" + request.getRequestURI() + "] consume time:" + t);
        }
        if (ex != null) {
            logger.error(ex.getMessage(), ex);
        }
        MDC.clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
        String requestId = request.getHeader(REQUEST_ID);
        logger.info("Header: requestId({})", requestId);
        if (!StringUtils.isEmpty(requestId)) {
            requestId = request.getParameter(REQUEST_ID);
            logger.info("Body: requestId({})", requestId);
        }

        if (StringUtils.isEmpty(requestId)) {
            requestId = System.currentTimeMillis() + "";
        }

        MDC.put(REQUEST_ID, requestId);
        logger.info(MessageFormat.format("url={0}, method={0}, queryString={0}", request.getRequestURL().toString(), request.getMethod().toLowerCase(), request.getQueryString()));

        return true;
    }
}
