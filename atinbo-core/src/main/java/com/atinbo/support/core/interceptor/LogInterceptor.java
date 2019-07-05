package com.atinbo.support.core.interceptor;

import com.atinbo.support.core.framework.redis.RedisDao;
import com.atinbo.support.core.framework.redis.RedisKeyGenerator;
import com.atinbo.support.core.model.ResultVO;
import com.atinbo.support.core.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;


public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID = "requestId";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Long start = null;
    @Autowired
    private RedisDao redisDao;

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

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
        String requestId = request.getHeader(REQUEST_ID);
        logger.info("Header: requestId({})", requestId);
        if (StringUtils.isBlank(requestId)) {
            requestId = request.getParameter(REQUEST_ID);
            logger.info("Body: requestId({})", requestId);
        }

        if (StringUtils.isBlank(requestId)) {
            requestId = System.currentTimeMillis() + "";
        }

        MDC.put(REQUEST_ID, requestId);
        logger.info(MessageFormat.format("{0}, {1}", request.getMethod().toLowerCase(), request.getRequestURL().toString()));
        String queryString = request.getQueryString();
        String paramString = WebUtil.getRequestParams(request);
        logger.info("contentType: {}, queryString: {}, paramString: {}", request.getContentType(), queryString, paramString);

        String repeatRequestKey = RedisKeyGenerator.generate(null, request.getRequestURL().toString(), requestId);
        String repeatRequestValue = (String) redisDao.getAndSet(repeatRequestKey, "1");
        redisDao.expire(repeatRequestKey, 500L, TimeUnit.MILLISECONDS);

        if (("1").equals(repeatRequestValue)) {
            ResultVO resultVO = ResultVO.errorMsg("Request too frequent!");
            String resultString = resultVO.toJsonString();
            logger.warn(resultString);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resultString);
            return false;
        }

        return true;
    }
}
