package com.atinbo.core.exception;


/**
 * 请求参数错误异常
 * 请使用 atinbo-webmvc模块里的异常类
 *
 * @author breggor
 */
@Deprecated
public class RequestParamException extends IllegalArgumentException {

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParamException(Throwable cause) {
        super(cause);
    }

}
