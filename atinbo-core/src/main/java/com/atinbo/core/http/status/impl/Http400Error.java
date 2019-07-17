package com.atinbo.core.http.status.impl;


import com.atinbo.core.http.status.IHttpError400;

public enum Http400Error implements IHttpError400<Http400Error> {
    INVALID_PARAM(400001, "非法参数"),
    CREATE_FAILED(400014, "创建失败"),
    UPDATE_ERROR(400002, "更新异常"),
    DELETE_ERROR(400003, "删除异常"),
    EABLED_ERROR(400004, "启用/禁用异常"),
    ;


    private Integer code;
    private String message;

    Http400Error(Integer code, String message) {
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
