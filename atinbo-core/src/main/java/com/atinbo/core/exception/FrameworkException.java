package com.atinbo.core.exception;


import com.atinbo.core.http.status.IHttpError;

/**
 * @author breggor
 */
public abstract class FrameworkException extends Exception {
    protected IHttpError error;

    public FrameworkException(IHttpError error) {
        this.error = error;
    }

    public IHttpError getError() {
        return this.error;
    }

    public FrameworkException setErrorEnum(IHttpError error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return "FrameworkException{errorEnum=" + this.error + '}';
    }
}
