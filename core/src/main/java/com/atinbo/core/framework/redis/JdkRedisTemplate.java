package com.atinbo.core.framework.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


public class JdkRedisTemplate extends RedisTemplate<String, Object> {

    /**
     * Constructs a new <code>JdkRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public JdkRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<Object> jdkSerializer = new JdkSerializationRedisSerializer();
        setKeySerializer(stringSerializer);
        setValueSerializer(jdkSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jdkSerializer);
    }

    /**
     * Constructs a new <code>JdkRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public JdkRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
