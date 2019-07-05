package com.atinbo.support.starter;

import com.alibaba.fastjson.JSON;
import com.atinbo.support.exceptions.APIException;
import com.atinbo.support.exceptions.ExceptionResult;
import feign.FeignException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrameworkExceptionHandler extends Handler {
    private static final String SPRING_404_EXCEPTION_MESSAGE = "org.springframework.web.servlet.resource.ResourceHttpRequestHandler cannot be cast to org.springframework.web.method.HandlerMethod";

    public FrameworkExceptionHandler() {
    }

    @Override
    public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            super.next.handle(url, request, response);
        } catch (NestedServletException var5) {
            if (var5.getCause() instanceof APIException) {
                this.flushFrameworkException(response, (APIException) var5.getCause());
            } else if (var5.getCause() instanceof ClassCastException && var5.getMessage().contains(SPRING_404_EXCEPTION_MESSAGE)) {
                response.setStatus(404);
            } else {
                if (var5.getCause() instanceof FeignException) {
                    this.flushFeignException(response, (FeignException) var5.getCause());
                    throw var5;
                }

                if (var5.getCause() instanceof Exception) {
                    this.flushException500(response, (Exception) var5.getCause());
                    throw var5;
                }
            }
        } catch (Exception var6) {
            this.flushException500(response, var6);
            throw var6;
        }

    }

    private void flushFrameworkException(HttpServletResponse response, APIException ex) {
        ExceptionResult result = new ExceptionResult(ex.getError());
        response.setStatus(ex.getError().getHttpCode());
        HttpRender.flushJson(response, result);
    }

    private void flushFeignException(HttpServletResponse response, FeignException ex) {
        int errorMessageIndex = ex.getMessage().indexOf("content:\n") + 9;
        String errorMessage = ex.getMessage().substring(errorMessageIndex);

        try {
            ExceptionResult result = (ExceptionResult) JSON.parseObject(errorMessage, ExceptionResult.class);
            response.setStatus(ex.status());
            HttpRender.flushJson(response, result);
        } catch (Exception var6) {
            this.flushException500(response, var6);
        }

    }

    private void flushException500(HttpServletResponse response, Exception e) {
        response.setStatus(500);
        ExceptionResult result = new ExceptionResult();
        result.setMessage(e.getMessage());
        result.setCode(500);
        result.addError("Internal Server Error", e.getMessage());
        HttpRender.flushJson(response, result);
    }
}
