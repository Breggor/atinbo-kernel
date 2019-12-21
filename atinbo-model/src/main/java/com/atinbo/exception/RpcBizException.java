package com.atinbo.exception;


/**
 * RPC业务异常
 *
 * @author breggor
 */
public class RpcBizException extends RuntimeException {

    public RpcBizException(String message) {
        super(message);
    }

    public RpcBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcBizException(Throwable cause) {
        super(cause);
    }

}
