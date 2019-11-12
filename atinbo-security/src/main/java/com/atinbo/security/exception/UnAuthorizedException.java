package com.atinbo.security.exception;

import org.springframework.http.HttpStatus;


/**
 * 未授权异常
 *
 * @author breggor
 */
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(HttpStatus status, String msg) {
        super(status.toString() + ": " + msg);
    }
}
