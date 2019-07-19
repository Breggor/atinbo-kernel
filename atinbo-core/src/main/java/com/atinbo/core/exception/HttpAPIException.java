package com.atinbo.core.exception;


import com.atinbo.core.http.status.HttpStatusCode;

/**
 * 接口异常
 *
 * @author breggor
 */
public class HttpAPIException extends FrameworkException {
    public HttpAPIException(HttpStatusCode error) {
        super(error);
    }

    @Override
    public String toString() {
        return "APIException{http=" + super.error + '}';
    }
}
