package com.atinbo.exception;


/**
 * RPC业务异常，业务处理异常与业务参数检查异常分开处理,@See RpcArgCheckException
 * 各个业务模块服务抛出异常，建议扩展RpcBizException做具体异常声明。<br/>
 * <p>
 * 不然网关与Controller无法识别异常处理@ExceptionHandler,dubbo ExceptionFilter重新创建RuntimeException
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
