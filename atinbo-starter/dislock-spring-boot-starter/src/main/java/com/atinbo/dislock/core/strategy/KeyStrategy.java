package com.atinbo.dislock.core.strategy;

import com.atinbo.dislock.constant.DisLockConsts;
import com.atinbo.dislock.core.LockKey.Builder;
import com.atinbo.dislock.exception.KeyBuilderException;

import java.lang.reflect.Method;

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
     * 生成构建类
     */
    public abstract Builder generateBuilder() throws KeyBuilderException;

}
