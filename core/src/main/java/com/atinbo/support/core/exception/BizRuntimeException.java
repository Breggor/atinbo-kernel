package com.atinbo.support.core.exception;

public class BizRuntimeException extends RuntimeException {

    private String code = null; //异常代码

    /**
     * 构造函数
     */
    public BizRuntimeException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param code    异常编码
     * @param message 异常信息
     */
    public BizRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public BizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public BizRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param cause 异常类
     */
    public BizRuntimeException(Throwable cause) {
        super(cause);
    }

    protected String getCode() {
        return code;
    }
}
