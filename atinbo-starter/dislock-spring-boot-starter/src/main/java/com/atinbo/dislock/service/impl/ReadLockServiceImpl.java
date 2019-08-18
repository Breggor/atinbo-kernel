package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.AbstractLockService;
import com.atinbo.dislock.service.LockService;
import org.redisson.api.RLock;

import java.util.Objects;

/**
 * 读锁操作服务
 *
 * @author breggor
 */
public class ReadLockServiceImpl extends AbstractLockService implements LockService {

    @Override
    public void lock() throws Exception {
        KeyInfo keyInfo = getKeyInfo();
        Objects.requireNonNull(keyInfo, "keyInfo: 不能为null");

        RLock lock = getRedissonClient().getReadWriteLock(keyInfo.getKeys().get(0)).readLock();
        setLock(lock);

        if (!isLeaseTime(keyInfo) && !isWaitTime(keyInfo)) {
            lock.lock();
            return;
        }

        if (isLeaseTime(keyInfo) && isWaitTime(keyInfo)) {
            lock.lock(keyInfo.getLeaseTime(), keyInfo.getTimeUnit());
            return;
        }

        if (isLeaseTime(keyInfo) && isWaitTime(keyInfo)) {
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
