package com.atinbo.redis.template;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * jdk序列化
 *
 * @author breggor
 */
public class JdkRedisTemplate extends RedisTemplate<String, Object> {
    private final static RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    private final static RedisSerializer<Object> jdkSerializer = new JdkSerializationRedisSerializer();

    public JdkRedisTemplate() {
        setKeySerializer(stringSerializer);
        setValueSerializer(jdkSerializer);

        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jdkSerializer);
    }

    public JdkRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
