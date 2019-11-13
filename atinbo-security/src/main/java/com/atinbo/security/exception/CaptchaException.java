package com.atinbo.security.exception;

/**
 * 验证码错误异常类
 *
 * @author breggor
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}