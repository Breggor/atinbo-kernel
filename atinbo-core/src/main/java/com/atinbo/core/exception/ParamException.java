package com.atinbo.core.exception;


/**
 * 参数异常
 *
 * @author breggor
 */
public class ParamException extends RuntimeException {

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

}
