package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.LockService;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 红锁操作服务
 *
 * @author breggor
 */
public class RedLockServiceImpl implements LockService {

    @Qualifier("lockRedissonClient")
    @Autowired
    private RedissonClient lockRedissonClient;

    private KeyInfo keyInfo;

    private RedissonRedLock lock;


    @Override
    public void setKeyInfo(KeyInfo keyInfo) {
        this.keyInfo = keyInfo;
    }

    @Override
    public void lock() throws Exception {
        RLock[] lockList = new RLock[keyInfo.getKeys().size()];
        for (int i = 0; i < keyInfo.getKeys().size(); i++) {
            lockList[i] = lockRedissonClient.getLock(keyInfo.getKeys().get(i));
        }

        lock = new RedissonRedLock(lockList);
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
        lock.unlock();
    }

}
