package com.atinbo.core.model;


import com.atinbo.model.StatusCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 错误信息返回封装
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrResult implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * 错误信息列表
     */
    @ApiModelProperty(value = "错误信息列表")
    private List<ErrorInfo> errors;


    public ErrResult(StatusCode error) {
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    public static ErrResult error(int code, String message) {
        return new ErrResult().setCode(code).setMessage(message);
    }

    public static ErrResult error(StatusCode statusCode, Map<String, String> errs) {
        ErrResult result = new ErrResult();
        result.setCode(statusCode.getCode());
        result.setMessage(statusCode.getMessage());
        if (null != errs && !errs.isEmpty()) {
            errs.entrySet().stream().forEach(obj -> result.addError(obj.getKey(), obj.getValue()));
        }
        return result;
    }

    public static ErrResult error(int httpCode, String message, String reason) {
        ErrResult result = new ErrResult();
        result.setCode(httpCode);
        result.setMessage(message);
        result.addError(reason);
        return result;
    }

    private void addError(String reason) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ErrorInfo().setReason(reason));
    }

    private void addError(String reason, String message) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ErrorInfo().setReason(reason).setMessage(message));
    }


    /**
     * 错误信息
     *
     * @author breggor
     */
    @Data
    @Accessors(chain = true)
    public static class ErrorInfo implements Serializable {
        /**
         * 原因
         */
        @ApiModelProperty(value = "错误原因")
        private String reason;

        /**
         * 消息
         */
        @ApiModelProperty(value = "错误明细")
        private String message;
    }
}