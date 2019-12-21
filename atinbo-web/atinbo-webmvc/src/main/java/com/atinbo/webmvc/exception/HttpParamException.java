package com.atinbo.webmvc.exception;


/**
 * 请求参数错误异常
 *
 * @author breggor
 */
public class HttpParamException extends IllegalArgumentException {

    public HttpParamException(String message) {
        super(message);
    }

    public HttpParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpParamException(Throwable cause) {
        super(cause);
    }

}
