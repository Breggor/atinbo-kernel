package com.atinbo.dislock.service.impl;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.service.AbstractLockService;
import com.atinbo.dislock.service.LockService;
import org.redisson.api.RLock;

/**
 * 公平锁操作服务
 *
 * @author breggor
 */
public class FairLockServiceImpl extends AbstractLockService implements LockService {

    @Override
    public RLock getLock(KeyInfo keyInfo) {
        return getRedissonClient().getFairLock(keyInfo.getKeys().get(0));
    }
}
