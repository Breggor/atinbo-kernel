package com.atinbo.support.constants;

import com.atinbo.support.constants.ICodeEnum;

public interface ILabelCodeEnum<T extends Enum<T>, C> extends ICodeEnum<T, C> {
    String getLabel();
}
