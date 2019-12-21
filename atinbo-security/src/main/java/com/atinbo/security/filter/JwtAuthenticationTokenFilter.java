package com.atinbo.security.filter;

import com.atinbo.core.utils.ObjectUtil;
import com.atinbo.security.model.LoginUser;
import com.atinbo.security.service.UserTokenService;
import com.atinbo.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * token过滤器 验证token有效性
 *
 * @author breggor
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final static String LOGIN_PATH = "/login";
    @Autowired
    private UserTokenService userTokenService;


    private static final String REQUEST_ID = "requestId";
    ThreadLocal<Long> actTime = new ThreadLocal<>();


    public void before(HttpServletRequest request) {
        actTime.set(System.currentTimeMillis());
        String requestId = request.getHeader(REQUEST_ID);
        log.info("Header: requestId({})", requestId);

        if (!StringUtils.isEmpty(requestId)) {
            requestId = request.getParameter(REQUEST_ID);
            log.info("Body: requestId({})", requestId);
        }

        if (StringUtils.isEmpty(requestId)) {
            requestId = System.currentTimeMillis() + "";
        }

        MDC.put(REQUEST_ID, requestId);
        log.info(MessageFormat.format("url={0}, method={0}, queryString={0}", request.getRequestURL().toString(), request.getMethod().toLowerCase(), request.getQueryString()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("req path=[{}]", request.getRequestURI());
        before(request);

        if (!request.getRequestURI().contains(LOGIN_PATH)) {
            LoginUser loginUser = userTokenService.getLoginUser(request);
            if (ObjectUtil.isNotEmpty(loginUser) && Objects.isNull(SecurityUtils.getAuthentication())) {
                userTokenService.verifyToken(loginUser);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
        after(request);
    }


    public void after(HttpServletRequest request) {
        long consumeTime = System.currentTimeMillis() - actTime.get();
        log.info("[" + request.getRequestURI() + "] consume time:" + consumeTime);
        MDC.clear();
        actTime.remove();
    }
}