package com.atinbo.core.exception;


import com.atinbo.core.http.status.HttpStatusCode;

/**
 * @author breggor
 */
public abstract class FrameworkException extends Exception {
    protected HttpStatusCode error;

    public FrameworkException(HttpStatusCode error) {
        this.error = error;
    }

    public HttpStatusCode getError() {
        return this.error;
    }

    public FrameworkException setErrorEnum(HttpStatusCode error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return "FrameworkException{errorEnum=" + this.error + '}';
    }
}
