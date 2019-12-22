package com.atinbo.webmvc.exception;


import com.atinbo.model.StatusCodeEnum;

/**
 * 请求参数错误异常
 *
 * @author breggor
 */
public class HttpParamException extends HttpApiException {


    public HttpParamException(String message) {
        super(StatusCodeEnum.PARAM_VALID_ERROR, message);
    }


}
