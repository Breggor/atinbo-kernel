package com.atinbo.core.http.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 错误信息
 *
 * @author breggor
 */
@Data
@Accessors(chain = true)
public class ErrorInfo implements Serializable {
    /**
     * 原因
     */
    private String reason;

    /**
     * 消息
     */
    private String message;
}