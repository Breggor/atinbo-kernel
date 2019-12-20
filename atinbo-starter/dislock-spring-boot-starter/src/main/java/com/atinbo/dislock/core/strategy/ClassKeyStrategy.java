package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.core.KeyInfo;
import com.atinbo.dislock.core.KeyInfo.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Method;
import java.util.StringJoiner;

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
        Builder keyBuilder = KeyInfo.newBuilder();
        keyBuilder.appendKey(wrapKeyTag(new StringBuilder(getSimpleClassName()).append(".").append(methodName).toString()));
        return keyBuilder;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClassKeyStrategy.class.getSimpleName() + "[", "]")
                .add("className='" + className + "'")
                .add("methodName='" + methodName + "'")
                .toString();
    }
}
