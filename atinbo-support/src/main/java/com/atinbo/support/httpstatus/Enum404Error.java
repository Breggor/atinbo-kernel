package com.atinbo.support.httpstatus;

import com.atinbo.support.base.IError401;

public enum Enum404Error implements IError401<Enum401Error> {
    NOT_FOUND(404, "未找到"),


    ;
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
