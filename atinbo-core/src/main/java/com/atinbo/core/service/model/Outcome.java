package com.atinbo.core.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 服务接口层返回对象
 *
 * @param <T>
 * @author breggor
 */
@Data
@Accessors(chain = true)
public class Outcome<T extends BaseBO> {
    /**
     * 返回对象
     */
    private T data;
    /**
     * 是否成功
     */
    private boolean success = false;
    /**
     * 错误信息
     */
    private String error;

    public static <E extends BaseBO> Outcome<E> ofFail(String error) {
        return new Outcome<E>().setError(error);
    }
}
