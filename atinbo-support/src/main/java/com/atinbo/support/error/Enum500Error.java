package com.atinbo.support.error;

import com.atinbo.core.http.status.IError500;

public enum Enum500Error implements IError500<Enum500Error> {
    SYSTEM_ERROR(500001, "系统错误"),

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
