package com.atinbo.dislock.exception;

/**
 * 锁异常
 *
 * @author breggor
 */
public class LockException extends RuntimeException {

    public LockException(String ex) {
        super(ex);
    }
}
