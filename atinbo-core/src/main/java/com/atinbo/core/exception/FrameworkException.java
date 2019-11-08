package com.atinbo.core.exception;


import com.atinbo.model.StatusCode;

/**
 * @author breggor
 */
public abstract class FrameworkException extends RuntimeException {
    protected StatusCode status;

    public FrameworkException(StatusCode status) {
        this.status = status;
    }

    public StatusCode getStatus() {
        return this.status;
    }

    public FrameworkException setStatus(StatusCode status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "FrameworkException{status=" + this.status + '}';
    }
}
