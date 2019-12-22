package com.atinbo.exception;


/**
 * RPC参数校验异常
 *
 * @author breggor
 */
public class RpcArgCheckException extends IllegalArgumentException {

    public RpcArgCheckException(String message) {
        super(message);
    }

    public RpcArgCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcArgCheckException(Throwable cause) {
        super(cause);
    }

}
