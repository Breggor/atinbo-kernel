package com.atinbo.core.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 服务接口层返回对象
 *
 * @param <T>
 * @author breggor
 */
@Data
@Accessors(chain = true)
public class Outcome<T extends BaseBO> implements Serializable {
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

    /**
     * 失败返回结果
     *
     * @param error
     * @param <E>
     * @return
     */
    public static <E extends BaseBO> Outcome<E> ofFail(String error) {
        return new Outcome<E>().setError(error);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @param <E>
     * @return
     */
    public static <E extends BaseBO> Outcome<E> ofSuccess(E data) {
        return new Outcome<E>().setSuccess(true).setData(data);
    }
}
