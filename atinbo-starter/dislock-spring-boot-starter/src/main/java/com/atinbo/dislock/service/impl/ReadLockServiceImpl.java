package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.LockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 读锁操作服务
 *
 * @author breggor
 */
public class ReadLockServiceImpl implements LockService {

    @Qualifier("lockRedissonClient")
    @Autowired
    private RedissonClient lockRedissonClient;

    private KeyInfo keyInfo;

    private RLock lock;

    @Override
    public void setKeyInfo(KeyInfo keyInfo) {
        this.keyInfo = keyInfo;
    }

    @Override
    public void lock() throws Exception {
        this.lock = lockRedissonClient.getReadWriteLock(keyInfo.getKeys().get(0)).readLock();

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

        this.lock.lock();
    }

    @Override
    public void release() {
        this.lock.unlock();
    }

}
