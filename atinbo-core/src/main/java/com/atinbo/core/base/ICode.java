package com.atinbo.core.base;


/**
 * 信息代码
 *
 * @param <T>
 * @param <C>
 */
public interface ICode<T extends Enum<T>, C> {

    static ICode codeOf(Enum instance, Object code) {
        ICode sub = (ICode) instance;
        return (ICode) sub.codeOf(code);
    }

    C getCode();

    T codeOf(C value);
}
