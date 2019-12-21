package com.atinbo.webmvc.exception;


import com.atinbo.model.StatusCode;

/**
 * 接口异常
 *
 * @author breggor
 */
public class HttpApiException extends RuntimeException {

    protected StatusCode status;


    public HttpApiException(StatusCode statusCode) {
        this.status = statusCode;
    }

    public StatusCode getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "APIException{http=" + this.status + '}';
    }
}
