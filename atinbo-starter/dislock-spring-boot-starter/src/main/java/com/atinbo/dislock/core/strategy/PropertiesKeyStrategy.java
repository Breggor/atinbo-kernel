package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.annotation.Key;
import com.atinbo.dislock.core.LockKey.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 属性锁处理
 *
 * @author breggor
 */
public class PropertiesKeyStrategy extends KeyStrategy {

    public PropertiesKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
        super(className, methodName, realMethod, args);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Builder generateBuilder() throws KeyBuilderException {
        Builder keyBuilder = com.atinbo.dislock.core.LockKey.newBuilder();
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            Class objectClass = obj.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                if (null != field.getAnnotation(Key.class)) {
                    field.setAccessible(true);
                    try {
                        keyBuilder.appendKey(wrapKeyTag(field.get(obj).toString()));
                    } catch (IllegalArgumentException e) {
                        throw new KeyBuilderException("生成builder失败", e);
                    } catch (IllegalAccessException e) {
                        throw new KeyBuilderException("生成builder失败", e);
                    }
                }
            }
        }
        return keyBuilder;
    }

}
