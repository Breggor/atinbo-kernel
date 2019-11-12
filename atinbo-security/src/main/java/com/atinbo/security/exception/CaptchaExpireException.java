package com.atinbo.security.exception;

/**
 * 验证码失效异常类
 *
 * @author breggor
 */
public class CaptchaExpireException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jcaptcha.expire", null);
    }
}