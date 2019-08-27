package com.atinbo.dislock.exception;


/**
 * key构建异常
 *
 * @author breggor
 */
public class KeyBuilderException extends RuntimeException {

    private static final long serialVersionUID = 713051615398843448L;

    public KeyBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyBuilderException(String message) {
        super(message);
    }
}
