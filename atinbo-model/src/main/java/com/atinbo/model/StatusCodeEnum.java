package com.atinbo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http status码
 *
 * @author breggor
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum implements StatusCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 业务异常
     */
    FAILURE(-1, "业务异常"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(400, "请求参数有误"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(401, "请求未授权"),

    /**
     * 请求被拒绝
     */
    REQ_REJECT(403, "请求被拒绝"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(404, "404 没找到请求"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(405, "不支持当前请求方法"),

    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(415, "不支持当前媒体类型"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(500, "服务器异常");

    private int code;

    private String message;
}