package com.atinbo.core.http.model;

/**
 * 错误信息
 *
 * @author breggor
 */
public class ErrorInfo {
    /**
     * 消息
     */
    private String message;
    /**
     * 原因
     */
    private String reason;

    public String getMessage() {
        return message;
    }

    public ErrorInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public ErrorInfo setReason(String reason) {
        this.reason = reason;
        return this;
    }
}