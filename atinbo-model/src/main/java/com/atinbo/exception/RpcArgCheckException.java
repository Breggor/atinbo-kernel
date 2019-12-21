package com.atinbo.exception;


/**
 * RPC参数检测异常
 *
 * @author breggor
 */
public class RpcArgCheckException extends RpcBizException {

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
