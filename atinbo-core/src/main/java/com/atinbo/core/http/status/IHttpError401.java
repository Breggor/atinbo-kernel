package com.atinbo.core.http.status;

/**
 * http 401错误
 *
 * @param <T>
 * @author breggor
 */
public interface IHttpError401<T extends Enum<T>> extends IHttpError<T> {
    @Override
    default Integer httpCode() {
        return 401;
    }

    @Override
    default String reason() {
        return "[Unauthorized] - 当前请求未通过授权";
    }
}
