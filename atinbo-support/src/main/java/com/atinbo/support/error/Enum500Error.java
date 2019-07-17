package com.atinbo.support.error;

import com.atinbo.core.http.status.IHttpError500;

public enum Enum500Error implements IHttpError500<Enum500Error> {
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
