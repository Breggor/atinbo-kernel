package com.atinbo.core.http.status;

/**
 * 附带标注的http状态码
 *
 * @param <T>
 * @param <C>
 */
public interface IHttpStatusLabelCode<T extends Enum<T>> extends IHttpStatusCode<T> {
    /**
     * 标注
     *
     * @return
     */
    String getLabel();
}
