package com.atinbo.support.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 业务处理状态：
 * 数据异常：0，已处理：1，处理成功：2，处理失败：3
 * Created by Breggor on 2016/8/24 12:48.
 */
public final class StatusVO<T> {
    public static final StatusVO<Integer> FAILED = new StatusVO<>(3, "处理失败");
    public static final StatusVO<Integer> SUCCESS = new StatusVO<>(2, "处理成功");
    public static final StatusVO<Integer> PROCESSED = new StatusVO<>(1, "已处理");
    public static final StatusVO<Integer> DATA_EXCEP = new StatusVO<>(0, "数据异常");


    private T value;//状态值（用于业务逻辑半段）
    private String msg;//业务信息
    private String exception;//异常信息

    public StatusVO(T value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public StatusVO(T value, String msg, String exception) {
        this.value = value;
        this.msg = msg;
        this.exception = exception;
    }

    public T getValue() {
        return value;
    }

    public StatusVO setValue(T value) {
        this.value = value;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public StatusVO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getException() {
        return exception;
    }

    public StatusVO setException(String exception) {
        this.exception = exception;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusVO statusVO = (StatusVO) o;
        return new EqualsBuilder()
                .append(value, statusVO.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("msg", msg)
                .append("exception", exception)
                .toString();
    }
}
