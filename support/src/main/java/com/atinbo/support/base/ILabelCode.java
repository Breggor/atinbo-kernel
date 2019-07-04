package com.atinbo.support.base;

import com.atinbo.support.base.ICode;

public interface ILabelCode<T extends Enum<T>, C> extends ICode<T, C> {
    String getLabel();
}
