package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.AbstractLockService;
import com.atinbo.dislock.service.LockService;
import org.redisson.api.RLock;

/**
 * 可重入锁加锁服务
 *
 * @author breggor
 */
public class ReentrantLockServiceImpl extends AbstractLockService implements LockService {

    @Override
    public RLock getLock(KeyInfo keyInfo) {
        return getRedissonClient().getLock(keyInfo.getKeys().get(0));
    }
}