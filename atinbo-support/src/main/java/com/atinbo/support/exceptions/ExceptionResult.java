//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.exceptions;

import com.atinbo.support.base.IError;

import java.util.ArrayList;
import java.util.List;

public class ExceptionResult {
    private Integer code;
    private String message;
    private List<ExceptionResult.Error> errors = new ArrayList();

    public ExceptionResult() {
    }

    public ExceptionResult(IError error) {
        this.message = error.getMessage();
        this.code = (Integer) error.getCode();
        this.errors.add((new ExceptionResult.Error()).setReason(error.getReason()).setMessage(error.getMessage()));
    }

    public Integer getCode() {
        return this.code;
    }

    public ExceptionResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ExceptionResult setMessage(String message) {
        this.message = message == null ? null : message.trim();
        return this;
    }

    public List<ExceptionResult.Error> getErrors() {
        return this.errors;
    }

    public ExceptionResult setErrors(List<ExceptionResult.Error> errors) {
        this.errors = errors;
        return this;
    }

    public void addError(String reason, String message) {
        this.errors.add((new ExceptionResult.Error()).setReason(reason).setMessage(message));
    }

    public static class Error {
        private String domain;
        private String reason;
        private String message;

        public Error() {
        }

        public String getDomain() {
            return this.domain;
        }

        public ExceptionResult.Error setDomain(String domain) {
            this.domain = domain == null ? null : domain.trim();
            return this;
        }

        public String getReason() {
            return this.reason;
        }

        public ExceptionResult.Error setReason(String reason) {
            this.reason = reason == null ? null : reason.trim();
            return this;
        }

        public String getMessage() {
            return this.message;
        }

        public ExceptionResult.Error setMessage(String message) {
            this.message = message == null ? null : message.trim();
            return this;
        }
    }
}
