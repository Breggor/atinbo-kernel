package com.atinbo.core.http.status;


/**
 * http 403错误
 *
 * @param <T>
 */
public interface IError403<T extends Enum<T>> extends IError<T> {
    @Override
    default Integer httpCode() {
        return 403;
    }

    @Override
    default String reason() {
        return "[Forbidden] - 服务器拒绝执行该请求";
    }
}
