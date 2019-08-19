package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.AbstractLockService;
import com.atinbo.dislock.service.LockService;
import org.redisson.api.RLock;

import java.util.Objects;

/**
 * 可重入锁加锁服务
 *
 * @author breggor
 */
public class ReentrantLockServiceImpl extends AbstractLockService implements LockService {

    @Override
    public void lock() throws Exception {
        KeyInfo keyInfo = getKeyInfo();
        Objects.requireNonNull(keyInfo, "keyInfo: 不能为null");

        RLock lock = getRedissonClient().getLock(keyInfo.getKeys().get(0));
        setLock(lock);

        if (!enableLeaseTime(keyInfo) && !enableWaitTime(keyInfo)) {
            lock.lock();
            return;
        }

        if (enableLeaseTime(keyInfo) && !enableWaitTime(keyInfo)) {
            lock.lock(keyInfo.getLeaseTime(), keyInfo.getTimeUnit());
            return;
        }

        if (enableLeaseTime(keyInfo) && enableWaitTime(keyInfo)) {
            lock.tryLock(keyInfo.getWaitTime(), keyInfo.getLeaseTime(), keyInfo.getTimeUnit());
            return;
        }

        lock.lock();
    }

    @Override
    public void release() {
        this.getLock().unlock();
    }
}
