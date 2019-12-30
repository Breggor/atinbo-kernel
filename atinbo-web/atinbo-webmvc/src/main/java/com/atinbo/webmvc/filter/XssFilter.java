package com.atinbo.webmvc.filter;

import com.atinbo.common.StringPool;
import com.atinbo.webmvc.xss.XssHttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * XSS过滤
 *
 * @author breggor
 */
@Slf4j
@Component
@AllArgsConstructor
public class XssFilter extends OncePerRequestFilter {

    /**
     * 放行url
     */
    @Value("${xss.skipUrls:''}")
    private final String skipUrls;

    /**
     * 排除匹配模式
     */
    @Value("${xss.excludePatterns:'/**'}")
    private final String excludePatterns;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (isSkip(path)) {
            chain.doFilter(request, response);
        } else {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(xssRequest, response);
        }
    }

    private boolean isSkip(String path) {
        return Arrays.stream(excludePatterns.split(",")).anyMatch(path::startsWith)
                || Arrays.stream(skipUrls.split(",")).map(url -> url.replace("/**", StringPool.EMPTY)).anyMatch(path::startsWith);
    }
}