package com.atinbo.dislock.exception;

/**
 * 仓库为空的异常
 *
 * @author breggor
 */
public class StoreIsEmptyException extends RuntimeException {

    private static final long serialVersionUID = -8975192014996569811L;

    public StoreIsEmptyException(String msg) {
        super(msg);
    }

}
