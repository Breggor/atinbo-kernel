package com.atinbo.support.exceptions;


import com.atinbo.support.base.IError;

/**
 * @author breggor
 */
public abstract class FrameworkException extends Exception {
    protected IError error;

    public FrameworkException(IError error) {
        this.error = error;
    }

    public IError getError() {
        return this.error;
    }

    public FrameworkException setErrorEnum(IError error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return "FrameworkException{errorEnum=" + this.error + '}';
    }
}
