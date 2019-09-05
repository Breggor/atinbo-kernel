package com.atinbo.core.http.model;


import com.atinbo.model.Pagination;
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
public class ResultVO<T> implements Serializable {
    /**
     * 分页数据列表
     */
    private T data;

    /**
     * 分页信息
     */
    private Pagination page;

    /**
     * 状态码: 0:成功，-1：失败，业务异常：非零或非-1
     */
    private int code;
    /**
     * 信息
     */
    private String message;


    public ResultVO(T data) {
        this.data = data;
    }

    public ResultVO(Pagination page, T data) {
        this.page = Pagination.of(page.getCurrent(), page.getSize(), page.getTotalRows(), page.getTotalPages());
        this.data = data;
    }

    /**
     * @param data
     * @param <E>
     * @return
     */
    public static <E> ResultVO of(E data) {
        return new ResultVO(data);
    }

    public static <T> ResultVO of(Pagination page, T data) {
        return new ResultVO(page, data);
    }

    public static ResultVO success() {
        return new ResultVO().setMessage("成功");
    }

    public static ResultVO failure() {
        return new ResultVO().setCode(-1).setMessage("失败");
    }
}