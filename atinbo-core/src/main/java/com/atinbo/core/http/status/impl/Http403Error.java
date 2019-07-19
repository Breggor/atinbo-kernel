package com.atinbo.core.http.status.impl;

import com.atinbo.core.http.status.IHttpError403;

public enum Http403Error implements IHttpError403<Http403Error> {

    REQUEST_FORBIDDEN(403001, "禁止访问"),
    USER_IS_FORBIDDEN(403002, "用户被禁用"),
    ;

    private Integer code;
    private String message;

    Http403Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
