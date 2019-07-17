package com.atinbo.core.http.status;


/**
 * http 状态码
 *
 * @param <T>
 * @param <C>
 * @author breggor
 */
public interface IHttpStatusCode<T extends Enum<T>> {

    static IHttpStatusCode codeOf(Enum instance, Integer code) {
        IHttpStatusCode sub = (IHttpStatusCode) instance;
        return (IHttpStatusCode) sub.codeOf(code);
    }

    /**
     * 编码
     *
     * @return
     */
    Integer getCode();

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    T codeOf(Integer value);
}
