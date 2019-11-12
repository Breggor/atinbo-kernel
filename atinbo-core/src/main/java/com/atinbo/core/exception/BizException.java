package com.atinbo.core.exception;

/**
 * 自定义异常
 *
 * @author breggor
 */
public class BizException extends BaseException {
    private static final long serialVersionUID = 1L;

    public BizException(String module, String code, Object[] args, String error) {
        super(module, code, args, error);
    }

    public BizException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public BizException(String module, String error) {
        super(module, error);
    }

    public BizException(String code, Object[] args) {
        super(code, args);
    }

    public BizException(String error) {
        super(error);
    }
}