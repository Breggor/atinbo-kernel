package com.atinbo.support.base;


/**
 * 错误接口
 *
 * @param <T>
 */
public interface IError<T extends Enum<T>> extends ICode<T, Integer> {
    String reason();

    Integer httpCode();

    default String getReason() {
        return this.reason();
    }

    default Integer getHttpCode() {
        return this.httpCode();
    }

    String getMessage();

    default T codeOf(Integer code) {
        return null;
    }
}
