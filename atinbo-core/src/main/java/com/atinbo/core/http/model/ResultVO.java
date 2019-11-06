package com.atinbo.core.http.model;


import com.atinbo.model.Pagination;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "数据列表")
    private T data;

    /**
     * 分页信息
     */
    @ApiModelProperty(value = "分页信息")
    private Pagination page;

    /**
     * 状态码: 0:成功，-1：失败，业务异常：非零或非-1
     */
    @ApiModelProperty(value = "状态码")
    private int code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;


    public ResultVO(T data) {
        this.data = data;
    }

    public ResultVO(Pagination page, T data) {
        this.page = Pagination.of(page.getCurrent(), page.getSize(), page.getTotalRows(), page.getTotalPages());
        this.data = data;
    }

    /**
     * 数据返回结果
     *
     * @param data
     * @param <E>
     * @return
     */
    public static <E> ResultVO of(E data) {
        return new ResultVO(data);
    }

    /**
     * 分页与数据返回结果
     *
     * @param page
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultVO of(Pagination page, T data) {
        return new ResultVO(page, data);
    }

    /**
     * 根据状态判断是否成功
     *
     * @param status > 0 成功否则失败
     * @return
     */
    public static ResultVO status(int status) {
        return (status > 0) ? success() : failure();
    }

    /**
     * 默认成功
     *
     * @return
     */
    public static ResultVO success() {
        return new ResultVO().setMessage("成功");
    }

    /**
     * 默认失败
     *
     * @return
     */
    public static ResultVO failure() {
        return new ResultVO().setCode(-1).setMessage("失败");
    }

    /**
     * 原因失败
     *
     * @param msg
     * @return
     */
    public static ResultVO failure(String msg) {
        return new ResultVO().setCode(-1).setMessage(msg);
    }
}