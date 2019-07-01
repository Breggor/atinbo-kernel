//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.httpstatus;


import com.atinbo.support.constants.IErrorEnum;

public interface IError400Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 400;
    }

    default String configReason() {
        return "[Bad Request] - 请求参数不合法";
    }
}
