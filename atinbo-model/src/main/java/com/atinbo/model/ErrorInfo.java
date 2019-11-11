package com.atinbo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 错误信息
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ErrorInfo implements Serializable {

    /**
     * 参数字段
     */
    @ApiModelProperty(value = "参数字段")
    private String field;

    /**
     * 错误原因
     */
    @ApiModelProperty(value = "错误原因")
    private String reason;


    public static ErrorInfo of(String field, String reason) {
        return new ErrorInfo(field, reason);
    }

    public static List<ErrorInfo> of(Map<String, String> errs) {
        if (null != errs && !errs.isEmpty()) {
            return errs.entrySet().parallelStream().map(e -> ErrorInfo.of(e.getKey(), e.getValue())).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}