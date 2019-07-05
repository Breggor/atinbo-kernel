package com.atinbo.support.core.framework.mybatis;

import com.atinbo.support.core.framework.redis.JsonHashRedisTemplate;
import com.atinbo.support.core.framework.spring.SpringContextHolder;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * mybatis redis cache
 * mybatis集成redis缓存
 * 使用时需要在properties文件中配置参数cache.module
 *
 * @author Breggor
 * @date 2016-11-18
 */
public class MybatisRedisCache implements Cache {

    private static final String KEY_CACHE = "cache:%s:%s";
    private static Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
    private final String cacheModule;
    private final Gson gson = new Gson();
    /**
     * The ReadWriteLock.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private JsonHashRedisTemplate redisTemplate;
    private String id;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        SpringContextHolder.assertApplicationContextInjected();
        SpringContextHolder.assertStringValueInjected();
        cacheModule = SpringContextHolder.getProperty("cache.module");
        Validate.validState(StringUtils.isNotBlank(cacheModule), "cache.module参数未配置, 请在properties文件中配置cache.module");
        logger.debug("MybatisCacheModule:{}===>{}", cacheModule, id);

        this.id = String.format(KEY_CACHE, cacheModule, id);
        redisTemplate = SpringContextHolder.getBeanByType(JsonHashRedisTemplate.class);
        Validate.validState(redisTemplate != null, "请spring上下文中实例化：JsonHashRedisTemplate");
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        Map<Object, Object> result = redisTemplate.opsForHash().entries(id);
        return result.size();
    }

    @Override
    public void putObject(Object key, Object value) {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:key={}    ,value={}", key, value);
        redisTemplate.opsForHash().put(id, gson.toJson(key), value);
    }

    @Override
    public Object getObject(Object key) {
        Object value = redisTemplate.opsForHash().get(id, gson.toJson(key));
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:key={}    ,value={}", key, value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        return redisTemplate.opsForHash().delete(id, gson.toJson(key));
    }

    @Override
    public void clear() {
        redisTemplate.delete(id);
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}