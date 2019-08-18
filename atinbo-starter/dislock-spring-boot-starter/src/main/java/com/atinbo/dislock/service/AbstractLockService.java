package com.atinbo.dislock.service;

import com.atinbo.dislock.core.KeyInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * 锁服务有状态数据多线程处理
 */
public abstract class AbstractLockService implements LockService {
    /**
     * redisson 客户端
     */
    private RedissonClient redissonClient;

    /**
     * 线程本地锁信息
     */
    private ThreadLocal<KeyInfo> keyInfoThreadLocal = new ThreadLocal<>();

    /**
     * 线程本地锁对象
     */
    private ThreadLocal<RLock> lockThreadLocal = new ThreadLocal<>();

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    @Autowired
    public void setRedissonClient(@Qualifier("disLockRedissonClient") RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public KeyInfo getKeyInfo() {
        return keyInfoThreadLocal.get();
    }

    @Override
    public void setKeyInfo(KeyInfo keyInfo) {
        this.keyInfoThreadLocal.set(keyInfo);
    }

    public RLock getLock() {
        return lockThreadLocal.get();
    }

    public void setLock(RLock lock) {
        this.lockThreadLocal.set(lock);
    }
}
