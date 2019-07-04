package com.atinbo.support.httpstatus;

import com.atinbo.support.base.IError403;

public enum Enum403Error implements IError403<Enum403Error> {

    REQUEST_FORBIDDEN(403001, "禁止访问"),
    USER_IS_FORBIDDEN(403002, "用户被禁用"),
    WECHAT_MINI_USER_INFO_NOT_FOUND(4030101, "该用户的微信用户信息未找到"),
    ;

    private Integer code;
    private String message;

    Enum403Error(Integer code, String message) {
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

