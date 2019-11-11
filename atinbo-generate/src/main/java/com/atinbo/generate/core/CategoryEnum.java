package com.atinbo.generate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * 数据层模版支持 mybatis、hibernate
 * @author zenghao
 * @date 2019-11-11
 */
@Getter
@AllArgsConstructor
public enum CategoryEnum {

    MYBATIS("mybatis"),
    HIBERNATE("hibernate"),
    ;

    private String value;

    /**
     * 校验配置的类型是否支持，若不支持则使用 mybatis
     * @param category
     * @return
     */
    public static String check(String category){
        AtomicBoolean has = new AtomicBoolean(false);
        Stream.of(CategoryEnum.values()).forEach(e -> has.set(e.getValue().equalsIgnoreCase(category)));
        return has.get() ? category : MYBATIS.getValue();
    }
}
