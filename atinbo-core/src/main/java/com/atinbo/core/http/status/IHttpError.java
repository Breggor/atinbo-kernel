package com.atinbo.core.http.status;


/**
 * 错误接口
 *
 * @param <T>
 * @author breggor
 */
public interface IHttpError<T extends Enum<T>> extends IHttpStatusCode<T> {
    String reason();

    Integer httpCode();

    default String getReason() {
        return this.reason();
    }

    default Integer getHttpCode() {
        return this.httpCode();
    }

    String getMessage();

    @Override
    default T codeOf(Integer code) {
        return null;
    }
}
