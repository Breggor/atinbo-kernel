package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.constant.DisLockConsts;
import com.atinbo.dislock.core.KeyInfo.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * key策略
 *
 * @author breggor
 */
public abstract class KeyStrategy {

    protected String className;
    protected String methodName;
    protected Method realMethod;
    protected Object[] args;

    public KeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
        this.className = className;
        this.methodName = methodName;
        this.realMethod = realMethod;
        this.args = args;
    }

    public String getSimpleClassName() {
        return this.className.split("\\.")[this.className.split("\\.").length - 1];
    }

    /**
     * 包装锁的key标记
     *
     * @param valTag
     * @return
     */
    public String wrapKeyTag(String valTag) {
        return getSimpleClassName() + DisLockConsts.KEY_SPLIT_MARK + this.methodName
                + DisLockConsts.KEY_SPLIT_MARK + valTag;
    }

    /**
     * 检查分布式Key是否为null
     *
     * @param fieldVal
     * @param field
     */
    protected void checkLockKey(Object fieldVal, String field) {
        Objects.requireNonNull(fieldVal, () -> MessageFormat.format("[分布式锁] - LockKey={0},不能为空", field));
    }

    /**
     * 生成构建类
     */
    public abstract Builder generateBuilder() throws KeyBuilderException;

}
