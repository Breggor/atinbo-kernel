package com.atinbo.core.framework.mybatis;

import com.atinbo.core.framework.redis.RedisDao;
import org.apache.ibatis.cache.decorators.LoggingCache;

/**
 * mybatis集成redis缓存
 * 使用时需要在properties文件中配置参数cache.module
 * 并且在 application.xml 配置中配置redisDao
 *
 * @author zenghao
 * @date 2016-11-21
 * @see MybatisRedisCache
 * @see RedisDao
 */
public class LoggingRedisCache extends LoggingCache {

    public LoggingRedisCache(String id) {
        super(new MybatisRedisCache(id));
    }
}