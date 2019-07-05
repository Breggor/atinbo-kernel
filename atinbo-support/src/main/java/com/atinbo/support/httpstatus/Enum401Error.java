package com.atinbo.support.httpstatus;


import com.atinbo.support.base.IError401;

public enum Enum401Error implements IError401<Enum401Error> {
    UNAUTH(401000, "未登录"),
    USER_LOGIN_AUTH_FAILED(401001, "登陆授权失败，账号或密码错误"),
    WECHAT_AUTHENTICATION_FAILED(401002, "微信授权失败"),
    QQ_AUTHENTICATION_FAILED(401003, "QQ授权失败"),
    WECHAT_MINI_PROGRAM_AUTHENTICATION_FAILED(401004, "微信小程序授权失败"),
    SGW_SESSION_USER_NOT_FOUND(401001, "网关用户没有找到"),
    USER_AUTH_FAILED(401005, "用户授权失败");


    private Integer code;
    private String message;

    Enum401Error(Integer code, String message) {
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
