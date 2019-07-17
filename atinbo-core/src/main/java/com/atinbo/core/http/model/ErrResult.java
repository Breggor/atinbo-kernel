package com.atinbo.core.http.model;


import com.atinbo.core.http.status.IError;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ErrResult implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 信息
     */
    private String message;
    /**
     * 错误信息列表
     */
    private List<ErrorInfo> errors;


    public ErrResult(IError error) {
        this.message = error.getMessage();
        this.code = (Integer) error.getCode();
        this.addError(error.reason(), error.getMessage());
    }

    public static ErrResult error(Map<String, String> errs) {
        ErrResult result = new ErrResult();
        if (null != errs && !errs.isEmpty()) {
            errs.entrySet().stream().forEach(obj -> result.addError(obj.getKey(), obj.getValue()));
        }
        return result;
    }

    public static ErrResult error(String message) {
        ErrResult result = new ErrResult();
        result.setMessage(message);
        return result;
    }

    public void addError(String message, String reason) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ErrorInfo().setMessage(message).setReason(reason));
    }
}
