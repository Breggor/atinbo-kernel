package com.atinbo.dislock.service;

import com.atinbo.dislock.core.KeyInfo;

/**
 * 锁服务
 *
 * @author breggor
 */
public interface LockService {

    /**
     * 设置当前锁key信息
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
     * 是否启用离开时间
     *
     * @param keyInfo
     * @return
     */
    default boolean enableLeaseTime(KeyInfo keyInfo) {
        return keyInfo.getLeaseTime() != -1;
    }

    /**
     * 是否启用等待时间
     *
     * @param keyInfo
     * @return
     */
    default boolean enableWaitTime(KeyInfo keyInfo) {
        return keyInfo.getWaitTime() != -1;
    }
}
