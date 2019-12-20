package com.atinbo.handler;


import com.atinbo.core.exception.HttpApiException;
import com.atinbo.model.Outcome;
import com.atinbo.model.StatusCodeEnum;
import com.atinbo.utils.HttpRequestUtils;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 异常处理器
 *
 * @author breggor
 */
public class FrameworkExceptionHandler extends Handler {
    private static final String SPRING_404_EXCEPTION_MESSAGE = "org.springframework.web.servlet.resource.ResourceHttpRequestHandler cannot be cast to org.springframework.web.method.HandlerMethod";

    public FrameworkExceptionHandler() {
    }

    @Override
    public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            super.next.handle(url, request, response);
        } catch (NestedServletException e) {
            if (e.getCause() instanceof HttpApiException) {
                this.flushFrameworkException(response, (HttpApiException) e.getCause());
            } else if (e.getCause() instanceof ClassCastException && e.getMessage().contains(SPRING_404_EXCEPTION_MESSAGE)) {
                response.setStatus(404);
            } else {
                if (e.getCause() instanceof Exception) {
                    this.flushException500(response, (Exception) e.getCause());
                    throw e;
                }
            }
        } catch (Exception ex) {
            this.flushException500(response, ex);
            throw ex;
        }

    }

    private void flushFrameworkException(HttpServletResponse response, HttpApiException ex) {
        response.setStatus(ex.getStatus().getCode());
        HttpRequestUtils.flushJson(response, Outcome.failure(ex.getStatus()));
    }


    private void flushException500(HttpServletResponse response, Exception e) {
        response.setStatus(StatusCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        HttpRequestUtils.flushJson(response, Outcome.failure(StatusCodeEnum.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}