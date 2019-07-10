package com.atinbo.dal.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


public class JsonRedisTemplate extends RedisTemplate<String, Object> {

    /**
     * Constructs a new <status>JdkRedisTemplate</status> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public JsonRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
        setKeySerializer(stringSerializer);
        setValueSerializer(jsonSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jsonSerializer);
    }

    /**
     * Constructs a new <status>JdkRedisTemplate</status> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
