//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.constants;

import com.atinbo.support.constants.ICodeEnum;

public interface IErrorEnum<T extends Enum<T>> extends ICodeEnum<T, Integer> {
    String configReason();

    Integer configHttpCode();

    default String getReason() {
        return this.configReason();
    }

    default Integer getHttpCode() {
        return this.configHttpCode();
    }

    String getMessage();

    default T codeOf(Integer code) {
        return null;
    }
}
