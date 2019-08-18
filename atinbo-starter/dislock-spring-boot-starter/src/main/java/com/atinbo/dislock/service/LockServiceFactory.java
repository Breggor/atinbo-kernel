package com.atinbo.dislock.service;

import com.atinbo.dislock.constant.DisLockType;
import com.atinbo.dislock.exception.LockServiceNotFoundException;
import com.atinbo.dislock.service.impl.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.EnumMap;

/**
 * 服务Bean工厂
 *
 * @author breggor
 */
public class LockServiceFactory implements ApplicationContextAware {

    private static EnumMap<DisLockType, Class<?>> serviceMap = new EnumMap<>(DisLockType.class);

    static {
        serviceMap.put(DisLockType.REENTRANT, ReentrantLockServiceImpl.class);
        serviceMap.put(DisLockType.FAIR, FairLockServiceImpl.class);
        serviceMap.put(DisLockType.MULTI, MultiLockServiceImpl.class);
        serviceMap.put(DisLockType.READ, ReadLockServiceImpl.class);
        serviceMap.put(DisLockType.RED, RedLockServiceImpl.class);
        serviceMap.put(DisLockType.WRITE, WriteLockServiceImpl.class);
    }

    private ApplicationContext applicationContext;

    /**
     * 根据锁类型获取相应的服务处理类
     *
     * @param lockType
     * @return
     * @throws LockServiceNotFoundException
     */
    public LockService getService(DisLockType lockType) throws LockServiceNotFoundException {
        LockService lockService = (LockService) applicationContext.getBean(serviceMap.get(lockType));
        if (lockService == null) {
            throw new LockServiceNotFoundException();
        }
        return lockService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
