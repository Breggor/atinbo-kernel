package com.atinbo.core.exception;


import com.atinbo.model.StatusCode;

/**
 * 接口异常
 *
 * @author breggor
 */
public class HttpApiException extends FrameworkException {
    public HttpApiException(StatusCode statusCode) {
        super(statusCode);
    }

    @Override
    public String toString() {
        return "APIException{http=" + super.status + '}';
    }
}
