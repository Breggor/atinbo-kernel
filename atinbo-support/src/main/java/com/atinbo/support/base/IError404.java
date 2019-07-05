package com.atinbo.support.base;


/**
 * http 404错误
 *
 * @param <T>
 */
public interface IError404<T extends Enum<T>> extends IError<T> {
    @Override
    default Integer httpCode() {
        return 404;
    }

    @Override
    default String reason() {
        return "[Not Found] -  请求失败:请求所希望得到的资源未被在服务器上发现";
    }
}
