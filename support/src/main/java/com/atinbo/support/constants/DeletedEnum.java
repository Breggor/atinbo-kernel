package com.atinbo.support.constants;

import com.atinbo.support.base.ILabelCode;

import java.util.Arrays;

public enum DeletedEnum implements ILabelCode<DeletedEnum, Integer> {
    NORMAL(0, "正常"),
    DELETED(1, "已删除");

    private Integer code;
    private String label;

    private DeletedEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public DeletedEnum codeOf(Integer code) {
        return (DeletedEnum) Arrays.stream(values()).filter((v) -> {
            return v.getCode().equals(code);
        }).findFirst().get();
    }
}
