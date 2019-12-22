package com.atinbo.webmvc.exception;


import com.atinbo.core.utils.MessageSourceUtil;
import com.atinbo.model.StatusCode;
import com.atinbo.model.StatusCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;

/**
 * 接口异常
 *
 * @author breggor
 */
@Data
public class HttpApiException extends ServletException {

    /**
     * 所属模块
     */
    protected String module;

    /**
     * 错误码
     */
    protected String code;

    /**
     * 错误码对应的参数
     */
    protected Object[] args;

    /**
     * 错误消息
     */
    protected String error;

    protected StatusCode status;


    public HttpApiException(StatusCode statusCode) {
        this.status = statusCode;
    }

    public HttpApiException(StatusCodeEnum status, String msg) {
        this.status = status;
        this.error = msg;
    }

    public StatusCode getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        String msg = null;
        if (StringUtils.isNotBlank(code)) {
            msg = MessageSourceUtil.message(code, args);
        }
        if (msg == null) {
            msg = error;
        }
        return msg;
    }

    @Override
    public String toString() {
        return "APIException{http=" + this.status + '}';
    }
}
