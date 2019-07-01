//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.httpstatus;


import com.atinbo.support.constants.IErrorEnum;

public interface IError503Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 503;
    }

    default String configReason() {
        return "[Overload] - 由于临时的服务器维护或者过载, 服务器当前无法处理请求.";
    }
}
