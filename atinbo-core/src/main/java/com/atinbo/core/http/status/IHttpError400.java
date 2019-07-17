package com.atinbo.core.http.status;


/**
 * http 400错误
 *
 * @param <T>
 * @author breggor
 */
public interface IHttpError400<T extends Enum<T>> extends IHttpError<T> {
    @Override
    default Integer httpCode() {
        return 400;
    }

    @Override
    default String reason() {
        return "[Bad Request] - 请求参数不合法";
    }
}
