package com.atinbo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 服务接口层返回对象
 *
 * @param <T>
 * @author breggor
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Outcome<T> implements Serializable {

    /**
     * 分页信息
     */
    @ApiModelProperty(value = "分页信息")
    private Page page = Page.EMPTY;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private T data;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String msg;

    /**
     * 状态码: 0:成功，-1：失败，业务异常：非零或非-1
     */
    @ApiModelProperty(value = "状态码", example = "状态码: 0:成功，-1：失败，业务异常：非零或非-1")
    private int code;


    public boolean isSuccess() {
        return code == 0;
    }

    public Outcome(T data) {
        this.data = data;
    }

    public Outcome(Page page, T data) {
        this.page = Page.of(page.getCurrent(), page.getSize(), page.getTotalRows(), page.getTotalPages());
        this.data = data;
    }

    /**
     * 失败返回结果
     *
     * @param error
     * @param <E>
     * @return
     */
    public static <E> Outcome<E> ofFail(String error) {
        return new Outcome<E>().setError(error);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @param <E>
     * @return
     */
    public static <E> Outcome<E> ofSuccess(E data) {
        return new Outcome<E>(data).setData(data).setMsg("成功");
    }

    /**
     * 分页与数据返回结果
     *
     * @param page
     * @param data
     * @param <E>
     * @return
     */
    public static <E> Outcome<E> ofSuccess(Page page, E data) {
        return new Outcome<E>(page, data).setMsg("成功");
    }

    /**
     * 默认成功
     *
     * @return
     */
    public static Outcome success() {
        return new Outcome().setMsg("成功");
    }


    /**
     * 默认失败
     *
     * @return
     */
    public static Outcome failure() {
        return new Outcome().setCode(-1).setMsg("失败");
    }


    /**
     * 原因失败
     *
     * @param error
     * @return
     */
    public static Outcome failure(String error) {
        return new Outcome().setCode(-1).setMsg("失败").setError(error);
    }


    /**
     * 根据状态判断是否成功
     *
     * @param status > 0 成功否则失败
     * @return
     */
    public static Outcome status(int status) {
        return (status > 0) ? success() : failure();
    }
}
