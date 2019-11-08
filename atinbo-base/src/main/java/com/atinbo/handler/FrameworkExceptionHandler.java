package com.atinbo.handler;


import com.atinbo.core.exception.HttpApiException;
import com.atinbo.core.model.ErrResult;
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
        ErrResult result = new ErrResult(ex.getStatus());
        response.setStatus(ex.getStatus().getCode());
        HttpRequestUtils.flushJson(response, result);
    }


    private void flushException500(HttpServletResponse response, Exception e) {
        response.setStatus(500);
        ErrResult result = ErrResult.error(StatusCodeEnum.INTERNAL_SERVER_ERROR.getCode(), StatusCodeEnum.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
        HttpRequestUtils.flushJson(response, result);
    }
}