//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.httpstatus;


import com.atinbo.support.constants.IErrorEnum;

public interface IError403Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 403;
    }

    default String configReason() {
        return "[Forbidden] - 服务器拒绝执行该请求";
    }
}
