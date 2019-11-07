package com.atinbo.core.exception;


/**
 * service异常
 *
 * @author breggor
 */
public class ServiceApiException extends RuntimeException {

    public ServiceApiException() {
        super();
    }

    public ServiceApiException(String message) {
        super(message);
    }

    public ServiceApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceApiException(Throwable cause) {
        super(cause);
    }
}
