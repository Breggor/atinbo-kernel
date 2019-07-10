package com.atinbo.core.http.status.impl;

import com.atinbo.core.http.status.IError404;

public enum Enum404Error implements IError404<Enum404Error> {
    NOT_FOUND(404001, "未找到");
    private Integer code;
    private String message;

    Enum404Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    /******* Fields Area *******/
    /******* Construction Area *******/
    /******* GetSet Area ******/
    /******* Method Area *******/
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
