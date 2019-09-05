package com.atinbo.core.exception;


/**
 * 业务错误异常
 *
 * @author breggor
 */
public class BizErrorException extends ServiceApiException {

    public BizErrorException(String message) {
        super(message);
    }

    public BizErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizErrorException(Throwable cause) {
        super(cause);
    }

}
