package com.atinbo.support.base;

/**
 * http 401错误
 *
 * @param <T>
 * @author breggor
 */
public interface IError401<T extends Enum<T>> extends IError<T> {
    @Override
    default Integer httpCode() {
        return 401;
    }

    @Override
    default String reason() {
        return "[Unauthorized] - 当前请求未通过授权";
    }
}
