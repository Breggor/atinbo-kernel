package com.atinbo.core.http.status;


/**
 * http 403错误
 *
 * @param <T>
 */
public interface IHttpError403<T extends Enum<T>> extends IHttpError<T> {
    @Override
    default Integer httpCode() {
        return 403;
    }

    @Override
    default String reason() {
        return "[Forbidden] - 服务器拒绝执行该请求";
    }
}
