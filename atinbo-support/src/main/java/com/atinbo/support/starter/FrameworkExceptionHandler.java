package com.atinbo.support.starter;

import com.atinbo.core.exception.HttpAPIException;
import com.atinbo.core.http.model.ErrResult;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.atinbo.core.http.status.HttpStatusCode.ERR_500;


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
            if (e.getCause() instanceof HttpAPIException) {
                this.flushFrameworkException(response, (HttpAPIException) e.getCause());
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

    private void flushFrameworkException(HttpServletResponse response, HttpAPIException ex) {
        ErrResult result = new ErrResult(ex.getError());
        response.setStatus(ex.getError().getHttpCode());
        HttpRender.flushJson(response, result);
    }


    private void flushException500(HttpServletResponse response, Exception e) {
        response.setStatus(500);
        ErrResult result = ErrResult.error(ERR_500.getHttpCode(), ERR_500.getMessage(), e.getMessage());
        HttpRender.flushJson(response, result);
    }
}