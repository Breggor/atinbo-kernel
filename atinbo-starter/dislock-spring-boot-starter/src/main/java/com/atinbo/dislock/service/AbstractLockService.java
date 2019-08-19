package com.atinbo.dislock.service;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.exception.LockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;


/**
 * 锁服务有状态数据多线程处理
 *
 * @author breggor
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

    @Override
    public void setKeyInfo(KeyInfo keyInfo) {
        this.keyInfoThreadLocal.set(keyInfo);
    }

    private void clear() {
        keyInfoThreadLocal.remove();
        lockThreadLocal.remove();
    }

    @Override
    public void lock() throws Exception {
        KeyInfo keyInfo = keyInfoThreadLocal.get();
        Objects.requireNonNull(keyInfo, "keyInfo: 不能为null");
        RLock lock = getLock(keyInfo);
        lockThreadLocal.set(lock);

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
        if (!this.lockThreadLocal.get().isHeldByCurrentThread()) {
            throw new LockException(String.format("[分布式锁] - 锁超时或者锁已释放: %s", keyInfoThreadLocal.get()));
        }
        this.lockThreadLocal.get().unlock();
        this.clear();
    }

    /**
     * 实现不同类型lock
     *
     * @param keyInfo
     * @return
     */
    protected abstract RLock getLock(KeyInfo keyInfo);
}