package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.annotation.LockKey;
import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.core.KeyInfo.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Method;
import java.util.StringJoiner;

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
        Builder keyBuilder = KeyInfo.newBuilder();
        for (int i = 0; i < realMethod.getParameters().length; i++) {
            if (realMethod.getParameters()[i].isAnnotationPresent(LockKey.class)) {
                keyBuilder.appendKey(wrapKeyTag(args[i].toString()));
            }
        }
        return keyBuilder;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ParameterKeyStrategy.class.getSimpleName() + "[", "]")
                .add("className='" + className + "'")
                .add("methodName='" + methodName + "'")
                .toString();
    }
}
