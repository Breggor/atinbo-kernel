package com.atinbo.context;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ThreadLocalContext {
    public static final ThreadLocal<Map<String, Object>> GLOBAL_THREAD_LOCAL = new InheritableThreadLocal();

    public static void set(String key, Object value) {
        if (!StringUtils.isEmpty(key) && !Objects.isNull(value)) {
            Map<String, Object> entry = GLOBAL_THREAD_LOCAL.get();
            if (entry == null) {
                entry = new HashMap();
            }
            entry.put(key, value);
            GLOBAL_THREAD_LOCAL.set(entry);
        } else {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for set , key : {%s}, value : {%s}" , key, value));
        }
    }

    public static void clean() {
        GLOBAL_THREAD_LOCAL.remove();
    }

    public static void remove(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for remove method , key : {%s}, value : {%s}" , key));
        } else {
            Map<String, Object> entry = GLOBAL_THREAD_LOCAL.get();
            if (entry != null) {
                entry.remove(key);
                if (entry.size() == 0) {
                    clean();
                } else {
                    GLOBAL_THREAD_LOCAL.set(entry);
                }
            }

        }
    }

    public static Object get(String key) {
        Object result = null;
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for remove method , key : {%s}, value : {%s}" , key));
        } else {
            Map<String, Object> entry = (Map) GLOBAL_THREAD_LOCAL.get();
            if (entry != null && entry.containsKey(key)) {
                result = entry.get(key);
            }

            return result;
        }
    }
}
