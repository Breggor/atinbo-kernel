package com.atinbo.core.exception;


/**
 * service异常
 *
 * @author breggor
 */
public class ServiceAPIException extends RuntimeException {

    public ServiceAPIException() {
        super();
    }

    public ServiceAPIException(String message) {
        super(message);
    }

    public ServiceAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceAPIException(Throwable cause) {
        super(cause);
    }
}
