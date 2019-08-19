package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.AbstractLockService;
import com.atinbo.dislock.service.LockService;
import org.redisson.api.RLock;

/**
 * 读锁操作服务
 *
 * @author breggor
 */
public class ReadLockServiceImpl extends AbstractLockService implements LockService {

    @Override
    public RLock getLock(KeyInfo keyInfo) {
        return getRedissonClient().getReadWriteLock(keyInfo.getKeys().get(0)).readLock();
    }
}
