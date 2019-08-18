package com.atinbo.dislock.exception;

/**
 * 没有找到相应的锁服务实现类
 *
 * @author breggor
 */
public class LockServiceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8199483743071016533L;

    public LockServiceNotFoundException() {
        super("没有找到相应的锁服务实现类");
    }

}
