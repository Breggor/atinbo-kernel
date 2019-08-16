package com.atinbo.dislock.service;

import com.atinbo.dislock.core.LockKey;

/**
 * 锁服务
 *
 * @author breggor
 */
public interface LockService {

    /**
     * 添加key
     *
     * @param lockKey
     */
    public void setLockKey(LockKey lockKey);

    /**
     * 加锁
     */
    public void lock() throws Exception;

    /**
     * 解锁
     */
    public void release();

}
