package com.atinbo.security.handler;

import com.atinbo.core.utils.ServletUtil;
import com.atinbo.model.Outcome;
import com.atinbo.model.StatusCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author breggor
 */
@Component
public class BaseAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String msg = String.format("请求访问：{}，{}: 认证失败，无法访问系统资源", request.getRequestURI(), HttpStatus.UNAUTHORIZED);
        ServletUtil.renderString(response, objectMapper.writeValueAsString(Outcome.failure(StatusCodeEnum.UN_AUTHORIZED, msg)));
    }
}
