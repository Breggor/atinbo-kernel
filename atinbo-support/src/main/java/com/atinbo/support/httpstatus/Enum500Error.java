package com.atinbo.support.httpstatus;

import com.atinbo.support.base.IError403;

public enum Enum500Error implements IError403<Enum500Error> {

    ;

    private Integer code;
    private String message;

    Enum500Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Integer getCode() {
        return null;
    }
}
