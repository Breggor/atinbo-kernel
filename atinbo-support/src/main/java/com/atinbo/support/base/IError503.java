//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.base;


public interface IError503<T extends Enum<T>> extends IError<T> {
    @Override
    default Integer httpCode() {
        return 503;
    }

    @Override
    default String reason() {
        return "[Overload] - 由于临时的服务器维护或者过载, 服务器当前无法处理请求.";
    }
}
