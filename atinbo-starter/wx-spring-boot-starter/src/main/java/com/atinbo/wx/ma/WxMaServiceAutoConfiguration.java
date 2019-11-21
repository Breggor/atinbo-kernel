package com.atinbo.wx.ma;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisConfigImpl;
import com.atinbo.wx.config.RedisProperties;
import com.atinbo.wx.config.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zenghao
 * @date 2019-11-21
 */
@Configuration
@ConditionalOnClass(WxMaService.class)
@RequiredArgsConstructor
public class WxMaServiceAutoConfiguration {

    private final WxMaProperties properties;

    @Autowired(required = false)
    private JedisPool jedisPool;

    @Bean
    @ConditionalOnMissingBean
    public WxMaService WxMaService(WxMaConfig wxMaConfig) {
        WxMaService WxMaService = new WxMaServiceImpl();
        WxMaService.setWxMaConfig(wxMaConfig);
        return WxMaService;
    }

    @Bean
    @ConditionalOnMissingBean(WxMaConfig.class)
    public WxMaConfig wxMaConfig(RedisProperties redisProperties) {
        StorageType type = properties.getType();

        if (type == StorageType.redis) {
            if(redisProperties == null){
                throw new RuntimeException("无法获取redis相关配置");
            }
            return wxMaRedisConfig(redisProperties);
        }
        return wxMaDefaultConfig();
    }

    private WxMaDefaultConfigImpl wxMaDefaultConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(properties.getAppId());
        config.setSecret(properties.getSecret());
        config.setToken(properties.getToken());
        config.setAesKey(properties.getAesKey());
        return config;
    }

    private WxMaRedisConfigImpl wxMaRedisConfig(RedisProperties redisProperties) {
        JedisPool poolToUse = jedisPool;
        if (poolToUse == null) {
            poolToUse = getJedisPool(redisProperties);
        }
        WxMaRedisConfigImpl config = new WxMaRedisConfigImpl();

        config.setAppid(properties.getAppId());
        config.setSecret(properties.getSecret());
        config.setToken(properties.getToken());
        config.setAesKey(properties.getAesKey());
        config.setJedisPool(poolToUse);
        return config;
    }

    private JedisPool getJedisPool(RedisProperties redis) {
        JedisPoolConfig config = new JedisPoolConfig();
        if (redis.getMaxActive() != null) {
            config.setMaxTotal(redis.getMaxActive());
        }
        if (redis.getMaxIdle() != null) {
            config.setMaxIdle(redis.getMaxIdle());
        }
        if (redis.getMaxWaitMillis() != null) {
            config.setMaxWaitMillis(redis.getMaxWaitMillis());
        }
        if (redis.getMinIdle() != null) {
            config.setMinIdle(redis.getMinIdle());
        }
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);

        JedisPool pool = new JedisPool(config, redis.getHost(), redis.getPort(),
                redis.getTimeout(), redis.getPassword(), redis.getDatabase());
        return pool;
    }
}
