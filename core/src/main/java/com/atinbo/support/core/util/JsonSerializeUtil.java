package com.atinbo.support.core.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;


public class JsonSerializeUtil {

    private static ObjectMapper mapper = null;

    static {
        mapper = new ObjectMapper();
        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
    }

    /**
     * json序列化
     *
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) return null;
        try {
            return mapper.writeValueAsBytes(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json反序列化
     *
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) return null;
        try {
            return mapper.readValue(bytes, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
