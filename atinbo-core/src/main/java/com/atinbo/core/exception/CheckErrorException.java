package com.atinbo.core.exception;


/**
 * 检查错误
 *
 * @author breggor
 */
public class CheckErrorException extends RuntimeException {

    public CheckErrorException(String message) {
        super(message);
    }

    public CheckErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckErrorException(Throwable cause) {
        super(cause);
    }

}
