package com.atinbo.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.util.concurrent.TimeUnit;

/**
 * @author zenghao
 * @date 2019-07-19
 */
@Slf4j
public class CaffeineDataRegion implements DomainDataStorageAccess {
    private static final int DEFAULT_EXPIRY_IN_SECONDS = 1800;
    /**
     * Region regionName
     */
    private final String regionName;
    private final Cache<Object, Object> cache;

    public CaffeineDataRegion(@NonNull String regionName) {
        this.regionName = StringUtils.replace(regionName, "." , ":") + ":" ;
        cache = Caffeine.newBuilder()
                // 设置cache中的数据在写入之后的存活时间
                .expireAfterWrite(DEFAULT_EXPIRY_IN_SECONDS, TimeUnit.SECONDS)
                // 构建cache实例
                .build();
        log.debug("caffeiene region={}, expiryInSeconds={}" , regionName, DEFAULT_EXPIRY_IN_SECONDS);
    }

    /**
     * confirm the specified key exists in current region
     *
     * @param key cache key
     * @return if cache key is exists in current region return true, else return
     * false
     */
    @Override
    public boolean contains(Object key) {
        try {
            log.debug("contains key={}" , key);
            return cache.getIfPresent(key) != null;
        } catch (Exception ignored) {
            log.warn("Fail to exists key. key={}" , key, ignored);
            return false;
        }
    }

    @Override
    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        try {
            return cache.getIfPresent(key);
        } catch (Exception ignored) {
            log.warn("Fail to get cache item... key={}" , key, ignored);
            return null;
        }
    }

    @Override
    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        try {
            cache.put(key, value);
        } catch (Exception ignored) {
            log.warn("Fail to put cache item... key={}," , key, ignored);
        }
    }

    @Override
    public void evictData() {
        try {
            cache.invalidateAll();
        } catch (Exception ignored) {
            log.warn("Fail to clear region... name={}" , regionName, ignored);
        }
    }

    @Override
    public void evictData(Object key) {
        try {
            cache.invalidate(key);
        } catch (Exception ignored) {
            log.warn("Fail to remove cache item... key={}" , key, ignored);
        }
    }

    @Override
    public void release() {
        cache.cleanUp();
        cache.invalidateAll();
    }
}