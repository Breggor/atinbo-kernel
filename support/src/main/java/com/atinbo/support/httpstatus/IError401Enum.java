//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.atinbo.support.httpstatus;

import com.atinbo.support.constants.IErrorEnum;

public interface IError401Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 401;
    }

    default String configReason() {
        return "[Unauthorized] - 当前请求未通过授权";
    }
}
