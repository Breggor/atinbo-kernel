package com.atinbo.core.exception;

/**
 * 用户验证异常
 * Created by Breggor on 2016/4/28.
 */
public class ParameterRuntimeException extends RuntimeException {

    public ParameterRuntimeException(String message) {
        super(message);
    }

    public ParameterRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterRuntimeException(Throwable cause) {
        super(cause);
    }

}
