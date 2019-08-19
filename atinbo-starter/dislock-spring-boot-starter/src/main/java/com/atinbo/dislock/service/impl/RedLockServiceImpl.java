package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.AbstractLockService;
import com.atinbo.dislock.service.LockService;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;

/**
 * 红锁操作服务
 *
 * @author breggor
 */
public class RedLockServiceImpl extends AbstractLockService implements LockService {

    @Override
    public RLock getLock(KeyInfo keyInfo) {
        RLock[] lockList = new RLock[keyInfo.getKeys().size()];
        for (int i = 0; i < keyInfo.getKeys().size(); i++) {
            lockList[i] = getRedissonClient().getLock(keyInfo.getKeys().get(i));
        }
        return new RedissonRedLock(lockList);
    }
}
