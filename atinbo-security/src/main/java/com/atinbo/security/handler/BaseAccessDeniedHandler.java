package com.atinbo.security.handler;

import com.atinbo.common.Charsets;
import com.atinbo.model.Outcome;
import com.atinbo.model.StatusCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author breggor
 * 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 * 包装失败信息到AccessDeniedException
 */
@Slf4j
@Component
@AllArgsConstructor
public class BaseAccessDeniedHandler implements AccessDeniedHandler {
    private final static String CONTENT_TYPE = "application/json; charset=utf-8";
    private final ObjectMapper objectMapper;

    /**
     * 授权拒绝处理，使用R包装
     *
     * @param request       request
     * @param response      response
     * @param authException authException
     */
    @Override
    @SneakyThrows
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) {
        log.info("授权失败，禁止访问 {}", request.getRequestURI());
        response.setCharacterEncoding(Charsets.UTF8_NAME);
        response.setContentType(CONTENT_TYPE);
        Outcome<AccessDeniedException> result = Outcome.failure(new AccessDeniedException("授权失败，禁止访问"));
        response.setStatus(StatusCodeEnum.REQ_REJECT.getCode());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
