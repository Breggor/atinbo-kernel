package com.atinbo.support.base;

public interface ILabelCode<T extends Enum<T>, C> extends ICode<T, C> {
    String getLabel();
}
