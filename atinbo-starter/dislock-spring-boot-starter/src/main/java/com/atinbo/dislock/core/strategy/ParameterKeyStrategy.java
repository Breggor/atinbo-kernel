package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.annotation.Key;
import com.atinbo.dislock.core.LockKey.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Method;

/**
 * 参数锁处理
 *
 * @author breggor
 */
public class ParameterKeyStrategy extends KeyStrategy {

    public ParameterKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
        super(className, methodName, realMethod, args);
    }

    @Override
    public Builder generateBuilder() throws KeyBuilderException {
        Builder keyBuilder = com.atinbo.dislock.core.LockKey.newBuilder();
        for (int i = 0; i < realMethod.getParameters().length; i++) {
            if (realMethod.getParameters()[i].isAnnotationPresent(Key.class)) {
                keyBuilder.appendKey(wrapKeyTag(args[i].toString()));
            }
        }
        return keyBuilder;
    }

}
