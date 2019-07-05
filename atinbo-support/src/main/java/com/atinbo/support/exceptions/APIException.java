
package com.atinbo.support.exceptions;


import com.atinbo.support.base.IError;

public class APIException extends FrameworkException {
    public APIException(IError error) {
        super(error);
    }

    public String toString() {
        return "APIException{errorEnum=" + super.error + '}';
    }
}
