package com.atinbo.redis.template;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * jackson序列化
 *
 * @author breggor
 */
public class JsonRedisTemplate extends RedisTemplate<String, Object> {
    private final static RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    private final static RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();

    public JsonRedisTemplate() {
        setKeySerializer(stringSerializer);
        setValueSerializer(jsonSerializer);

        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jsonSerializer);
    }

    public JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
