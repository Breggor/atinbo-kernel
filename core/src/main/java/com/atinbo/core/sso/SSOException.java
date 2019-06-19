package com.atinbo.core.sso;

/**
 * 登录异常
 *
 * @author Administrator
 */

public class SSOException extends Exception {
    //异常信息
    private String message;

    public SSOException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
