package com.atinbo.core.http.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @param <T>
 * @author Breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 分页数据列表
     */
    private T data;

    /**
     * 状态码: 0:成功，非零为失败
     */
    private int code = 0;
    /**
     * 信息
     */
    private String message;


    public Result(T data) {
        this.data = data;
    }

    /**
     * @param data
     * @param <E>
     * @return
     */
    public static <E> Result of(E data) {
        return new Result(data);
    }

    public static Result ofSuccess() {
        return new Result();
    }
}
