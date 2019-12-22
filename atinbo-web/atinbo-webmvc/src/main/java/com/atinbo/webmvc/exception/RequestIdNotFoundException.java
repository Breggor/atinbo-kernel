package com.atinbo.webmvc.exception;

import com.atinbo.model.StatusCodeEnum;

/**
 * 没有请求Id异常
 *
 * @author breggor
 */
public class RequestIdNotFoundException extends HttpApiException {
    public RequestIdNotFoundException(String msg) {
        super(StatusCodeEnum.PARAM_VALID_ERROR, msg);
    }
}
