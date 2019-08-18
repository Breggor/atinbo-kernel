package com.atinbo.dislock.service;

import com.atinbo.dislock.core.KeyInfo;

/**
 * 锁服务
 *
 * @author breggor
 */
public interface LockService {

    /**
     * 添加key
     *
     * @param keyInfo
     */
    public void setKeyInfo(KeyInfo keyInfo);

    /**
     * 加锁
     */
    public void lock() throws Exception;

    /**
     * 解锁
     */
    public void release();


    /**
     * 是否设置离开时间
     *
     * @param keyInfo
     * @return
     */
    default boolean isLeaseTime(KeyInfo keyInfo) {
        return keyInfo.getLeaseTime() != -1;
    }

    /**
     * 是否设置等待时间
     *
     * @param keyInfo
     * @return
     */
    default boolean isWaitTime(KeyInfo keyInfo) {
        return keyInfo.getWaitTime() != -1;
    }
}
