//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.core.http.status;


public interface IHttpError500<T extends Enum<T>> extends IHttpError<T> {
    @Override
    default Integer httpCode() {
        return 500;
    }

    @Override
    default String reason() {
        return "[Internal Server Error] - 服务器内部错误） 服务器遇到错误，无法完成请求.";
    }
}
