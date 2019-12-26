package com.atinbo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Optional;

/**
 * 返回数据封装类
 *
 * @param <T> 特别说明：不指定泛型类型时，表示data无返回值
 * @author breggor
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "返回信息")
public class Outcome<T> implements Serializable {

    /**
     * 状态码: 0:成功，-1：失败，业务异常：非零或非-1
     */
    @ApiModelProperty(value = "状态码", example = "状态码: 0:成功，-1：失败，业务异常：非零或非-1")
    private int code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private T data;

    /**
     * 状态码构造器
     *
     * @param statusCode
     */
    private Outcome(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    private Outcome(StatusCode statusCode, String message) {
        this(statusCode, message, null);
    }


    private Outcome(StatusCode statusCode, T data) {
        this(statusCode, statusCode.getMessage(), data);
    }

    private Outcome(StatusCode statusCode, String message, T data) {
        this(statusCode.getCode(), message, data);
    }

    private Outcome(int code, String message, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 返回值
     *
     * @return Optional<T>
     */
    public Optional<T> value() {
        return Optional.ofNullable(this.data);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @param <E>
     * @return
     */
    public static <T> Outcome<T> success(T data) {
        return new Outcome(StatusCodeEnum.SUCCESS, data);
    }

    /**
     * 成功返回结果
     *
     * @param code
     * @return
     */
    public static Outcome success(StatusCode code) {
        return new Outcome(code);
    }

    /**
     * 成功返回结果
     *
     * @param message
     * @return
     */
    public static Outcome success(String message) {
        return new Outcome(StatusCodeEnum.SUCCESS, message);
    }

    /**
     * 成功返回结果
     *
     * @param code
     * @param message
     * @return
     */
    public static Outcome success(StatusCode code, String message) {
        return new Outcome(code, message);
    }

    /**
     * 默认成功
     *
     * @return
     */
    public static Outcome success() {
        return new Outcome(StatusCodeEnum.SUCCESS);
    }

    /**
     * 默认失败
     *
     * @return
     */
    public static Outcome failure() {
        return new Outcome(StatusCodeEnum.FAILURE);
    }

    /**
     * 失败返回结果
     *
     * @param data
     * @return
     */
    public static <T> Outcome<T> failure(T data) {
        return new Outcome(StatusCodeEnum.FAILURE, data);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @return
     */
    public static Outcome failure(String message) {
        return new Outcome(StatusCodeEnum.FAILURE, message);
    }

    /**
     * 失败返回结果
     *
     * @param code
     * @param message
     * @return
     */
    public static Outcome failure(int code, String message) {
        return new Outcome(code, message, null);
    }

    /**
     * 原因失败
     *
     * @param statusCode
     * @return
     */
    public static Outcome failure(StatusCode statusCode) {
        return new Outcome(statusCode);
    }

    /**
     * 原因失败
     *
     * @param statusCode
     * @param data
     * @return
     */
    public static <T> Outcome<T> failure(StatusCode statusCode, T data) {
        return new Outcome(statusCode, data);
    }

    /**
     * 原因失败
     *
     * @param statusCode
     * @return
     */
    public static Outcome failure(StatusCode statusCode, String message) {
        return new Outcome(statusCode, message);
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

    /**
     * 根据状态判断是否成功
     *
     * @param status
     * @return
     */
    public static Outcome status(boolean status) {
        return status ? success() : failure();
    }

    @Transient
    public boolean ok() {
        return (0 == code || StatusCodeEnum.SUCCESS.getCode() == code);
    }
}