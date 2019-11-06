package com.atinbo.security.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(HttpStatus status, String msg) {
        super(status.toString() + ": " + msg);
    }
}
