package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.core.LockKey;
import com.atinbo.dislock.core.LockKey.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Method;

/**
 * 类名+方法名
 *
 * @author breggor
 */
public class ClassKeyStrategy extends KeyStrategy {

    public ClassKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
        super(className, methodName, realMethod, args);
    }

    @Override
    public Builder generateBuilder() throws KeyBuilderException {
        Builder keyBuilder = LockKey.newBuilder();
        keyBuilder.appendKey(wrapKeyTag(new StringBuilder(getSimpleClassName()).append(".").append(methodName).toString()));
        return keyBuilder;
    }

}
