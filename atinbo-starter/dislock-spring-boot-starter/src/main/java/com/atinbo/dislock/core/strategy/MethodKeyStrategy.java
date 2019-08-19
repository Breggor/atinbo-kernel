package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.annotation.Key;
import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.core.KeyInfo.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * 方法锁处理
 *
 * @author breggor
 */
public class MethodKeyStrategy extends KeyStrategy {

    public MethodKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
        super(className, methodName, realMethod, args);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Builder generateBuilder() throws KeyBuilderException {
        Builder keyBuilder = KeyInfo.newBuilder();
        String[] values = realMethod.getAnnotation(Key.class).value();
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            Class objectClass = obj.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (String value : values) {
                String[] propertyName = value.split("\\.");
                String[] className = objectClass.getName().split("\\.");
                if (propertyName[0].equals(className[className.length - 1])) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getName().equals(propertyName[1])) {
                            try {
                                keyBuilder.appendKey(wrapKeyTag(field.get(obj).toString()));
                            } catch (IllegalAccessException e) {
                                throw new KeyBuilderException("生成builder失败", e);
                            }
                        }
                    }
                }
            }
        }
        return keyBuilder;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MethodKeyStrategy.class.getSimpleName() + "[", "]")
                .add("className='" + className + "'")
                .add("methodName='" + methodName + "'")
                .toString();
    }
}
