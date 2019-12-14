package com.atinbo.redis;

import com.atinbo.redis.template.JsonRedisTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 权限配置
 *
 * @author breggor
 */
@ComponentScan(basePackageClasses = AtinboRedisCache.class)
public class AtinboRedisCache {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
        return new JsonRedisTemplate(connectionFactory);
    }
}