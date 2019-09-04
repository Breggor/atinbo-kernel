package com.atinbo.security.filter;

import com.atinbo.core.http.model.Result;
import com.atinbo.security.jwt.JwtTokenOps;
import com.atinbo.security.model.JwtUser;
import com.atinbo.security.model.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 自定义拦截
 *
 * @author breggor
 */
@Slf4j
public class CustomUsernamePasswordProcessingFilterFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtTokenOps jwtTokenOps;

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    public CustomUsernamePasswordProcessingFilterFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        setAuthenticationManager(authenticationManager);
    }

    public boolean isPostRequest(HttpServletRequest request) {
        return HttpMethod.POST.name().equals(request.getMethod());
    }

    public boolean isObjectRequest(HttpServletRequest request) {
        return isPostRequest(request) && isContentTypeJson(request);
    }

    public boolean isContentTypeJson(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.APPLICATION_JSON_VALUE);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isPostRequest(request)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = null;
        String password = null;
        if (isObjectRequest(request)) {
            UserCredentials user = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            if (user != null) {
                username = user.getUsername();
                password = user.getPassword();
            }
        } else {
            username = obtainUsername(request);
            password = obtainPassword(request);
        }
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        // 返回一个验证令牌
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        JwtUser principal = (JwtUser) authResult.getPrincipal();
        String token = jwtTokenOps.generateToken(principal);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", principal.getUserId());
        response.setHeader("Authorization", token);
        objectMapper.writeValue(response.getWriter(), Result.success().setData(data));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        objectMapper.writeValue(response.getWriter(), Result.failure().setMessage(failed.getMessage()));
        log.error(failed.getMessage(), failed);
    }
}