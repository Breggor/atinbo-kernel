package com.atinbo.core.exception;


import com.atinbo.core.http.status.IError;

/**
 * 接口异常
 *
 * @author breggor
 */
public class APIException extends FrameworkException {
    public APIException(IError error) {
        super(error);
    }

    @Override
    public String toString() {
        return "APIException{http=" + super.error + '}';
    }
}
