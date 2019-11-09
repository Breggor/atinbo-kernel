package com.atinbo.redis;


import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache 工具类
 * 具体模块使用说明：
 * <bean id="redisDao" class="RedisDao">
 * <property name="redisTemplate" ref="redisTemplate" />
 * </bean>
 */
public class RedisDao {

    private RedisTemplate<Serializable, Serializable> redisTemplate;

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Serializable> operations = redisTemplate
                .opsForValue();
        result = operations.get(key);
        return result;
    }

    public Serializable getAndSet(final Serializable key, final Serializable value) {
        Serializable result = null;
        ValueOperations<Serializable, Serializable> operations = redisTemplate
                .opsForValue();
        result = operations.getAndSet(key, value);
        return result;
    }

    /**
     * 读取hash键值对
     *
     * @param key
     * @return
     */
    public Map<String, String> hget(final String key) {
        Map<String, String> result = null;
        HashOperations<Serializable, String, String> operations = redisTemplate
                .opsForHash();
        result = operations.entries(key);
        return result;
    }


    /**
     * 存hash键值对
     *
     * @param key
     * @return
     */
    public void hput(final String key, Map<String, String> map) {
        HashOperations<Serializable, String, String> operations = redisTemplate
                .opsForHash();
        operations.putAll(key, map);
    }


    /**
     * 存hash键值对
     *
     * @param key
     * @return
     */
    public void hput(final String key, Map<String, String> map, long seconds) {
        HashOperations<Serializable, String, String> operations = redisTemplate
                .opsForHash();
        operations.putAll(key, map);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }


    public void hput(String key, String field, String value) {
        HashOperations<Serializable, String, String> operations = redisTemplate.opsForHash();
        operations.put(key, field, value);
    }


    /**
     * 读取 keys
     *
     * @param key
     * @return
     */
    public Set<Serializable> keys(final String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Serializable value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Serializable> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Serializable value, long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Serializable> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 不存在写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(final String key, Serializable value, long expire) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Serializable> operations = redisTemplate.opsForValue();
            result = operations.setIfAbsent(key, value);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void hset(String key, Object field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置key过期时间
     *
     * @param key
     * @param expire 秒
     * @return
     */
    public boolean expire(final String key, long expire) {
        boolean result = false;
        try {
            result = redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置key过期时间
     *
     * @param key
     * @return
     */
    public boolean expire(final String key, long expire, TimeUnit timeUnit) {
        boolean result = false;
        try {
            result = redisTemplate.expire(key, expire, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object hget(String key, Object field) {
        Object result = null;
        try {
            result = redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object hdel(String key, Object field) {
        Long result = null;
        try {
            result = redisTemplate.opsForHash().delete(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void del(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Object, Object> hgetAll(String key) {
        Map<Object, Object> result = null;
        try {
            result = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long incr(String key, long value) {
        try {
            return redisTemplate.opsForValue().increment(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Long incr(String key, long value, long seconds) {
        Long result = null;
        try {
            result = redisTemplate.opsForValue().increment(key, value);
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public long getIncrValue(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] rowkey = serializer.serialize(key);
                byte[] rowval = connection.get(rowkey);
                try {
                    String val = serializer.deserialize(rowval);
                    return Long.parseLong(val);
                } catch (Exception e) {
                    return 0L;
                }
            }
        });
    }
}