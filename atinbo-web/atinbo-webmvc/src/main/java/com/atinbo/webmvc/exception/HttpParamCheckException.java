package com.atinbo.webmvc.exception;


import com.atinbo.model.StatusCodeEnum;

/**
 * 请求参数检查异常
 *
 * @author breggor
 */
public class HttpParamCheckException extends HttpApiException {


    public HttpParamCheckException(String message) {
        super(StatusCodeEnum.PARAM_VALID_ERROR, message);
    }


}
