package com.atinbo.support.base;


import com.atinbo.support.base.IError;

/**
 * http 400错误
 *
 * @param <T>
 * @author breggor
 */
public interface IError400<T extends Enum<T>> extends IError<T> {
    @Override
    default Integer httpCode() {
        return 400;
    }

    @Override
    default String reason() {
        return "[Bad Request] - 请求参数不合法";
    }
}
