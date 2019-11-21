package com.atinbo.wx.mp;

import com.atinbo.wx.config.RedisProperties;
import com.atinbo.wx.config.StorageType;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 微信公众号相关服务自动注册.
 *
 * @author someone
 */
@Configuration
@ConditionalOnClass(WxMpService.class)
@RequiredArgsConstructor
public class WxMpServiceAutoConfiguration {
    private final ApplicationContext ctx;
    private final WxMpProperties properties;

    @Autowired(required = false)
    private JedisPool jedisPool;

    @Bean
    @ConditionalOnMissingBean(WxMpConfigStorage.class)
    public WxMpConfigStorage wxMpInMemoryConfigStorage(RedisProperties redisProperties) {
        StorageType type = properties.getType();

        if (type == StorageType.redis) {
            if(redisProperties == null){
                throw new RuntimeException("无法获取redis相关配置");
            }
            return getWxMpInRedisConfigStorage(redisProperties);
        }
        return getWxMpInMemoryConfigStorage();
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        registerWxMpSubService(wxMpService);
        return wxMpService;
    }

    @ConditionalOnBean(WxMpService.class)
    public Object registerWxMpSubService(WxMpService wxMpService) {
        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        factory.registerSingleton("wxMpKefuService" , wxMpService.getKefuService());
        factory.registerSingleton("wxMpMaterialService" , wxMpService.getMaterialService());
        factory.registerSingleton("wxMpMenuService" , wxMpService.getMenuService());
        factory.registerSingleton("wxMpUserService" , wxMpService.getUserService());
        factory.registerSingleton("wxMpUserTagService" , wxMpService.getUserTagService());
        factory.registerSingleton("wxMpQrcodeService" , wxMpService.getQrcodeService());
        factory.registerSingleton("wxMpCardService" , wxMpService.getCardService());
        factory.registerSingleton("wxMpDataCubeService" , wxMpService.getDataCubeService());
        factory.registerSingleton("wxMpUserBlacklistService" , wxMpService.getBlackListService());
        factory.registerSingleton("wxMpStoreService" , wxMpService.getStoreService());
        factory.registerSingleton("wxMpTemplateMsgService" , wxMpService.getTemplateMsgService());
        factory.registerSingleton("wxMpSubscribeMsgService" , wxMpService.getSubscribeMsgService());
        factory.registerSingleton("wxMpDeviceService" , wxMpService.getDeviceService());
        factory.registerSingleton("wxMpShakeService" , wxMpService.getShakeService());
        factory.registerSingleton("wxMpMemberCardService" , wxMpService.getMemberCardService());
        factory.registerSingleton("wxMpMassMessageService" , wxMpService.getMassMessageService());
        return Boolean.TRUE;
    }

    private WxMpDefaultConfigImpl getWxMpInMemoryConfigStorage() {
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        setWxMpInfo(config);
        return config;
    }

    private WxMpRedisConfigImpl getWxMpInRedisConfigStorage(RedisProperties redisProperties) {
        JedisPool poolToUse = jedisPool;
        if (poolToUse == null) {
            poolToUse = getJedisPool(redisProperties);
        }
        WxMpRedisConfigImpl config = new WxMpRedisConfigImpl(poolToUse);
        setWxMpInfo(config);
        return config;
    }

    private void setWxMpInfo(WxMpDefaultConfigImpl config) {
        config.setAppId(properties.getAppId());
        config.setSecret(properties.getSecret());
        config.setToken(properties.getToken());
        config.setAesKey(properties.getAesKey());
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
