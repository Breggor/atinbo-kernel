package com.atinbo.core.exception;


import com.atinbo.model.StatusCode;

/**
 * 接口异常
 *
 * @author breggor
 */
public class HttpApiException extends RuntimeException {

    protected StatusCode status;


    public StatusCode getStatus() {
        return this.status;
    }

    public HttpApiException(StatusCode statusCode) {
        this.status = statusCode;
    }

    @Override
    public String toString() {
        return "APIException{http=" + this.status + '}';
    }
}
