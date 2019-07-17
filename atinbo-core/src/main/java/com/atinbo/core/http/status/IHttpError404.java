package com.atinbo.core.http.status;


/**
 * http 404错误
 *
 * @param <T>
 */
public interface IHttpError404<T extends Enum<T>> extends IHttpError<T> {
    @Override
    default Integer httpCode() {
        return 404;
    }

    @Override
    default String reason() {
        return "[Not Found] -  请求失败:请求所希望得到的资源未被在服务器上发现";
    }
}
