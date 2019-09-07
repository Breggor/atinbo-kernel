package com.atinbo.core.exception;


import com.atinbo.core.constants.HttpStatusCode;

/**
 * 接口异常
 *
 * @author breggor
 */
public class HttpApiException extends FrameworkException {
    public HttpApiException(HttpStatusCode error) {
        super(error);
    }

    @Override
    public String toString() {
        return "APIException{http=" + super.error + '}';
    }
}
