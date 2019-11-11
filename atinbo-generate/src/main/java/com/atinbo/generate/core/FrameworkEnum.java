package com.atinbo.generate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * 框架层模版支持 dubbo、springCloud
 * @author zenghao
 * @date 2019-11-11
 */
@Getter
@AllArgsConstructor
public enum FrameworkEnum {

    DUBBO("dubbo"),
    SPRING_CLOUD("springCloud"),
    ;

    private String value;

    /**
     * 校验配置的类型是否支持，若不支持则使用 dubbo
     * @param framework
     * @return
     */
    public static String check(String framework){
        AtomicBoolean has = new AtomicBoolean(false);
        Stream.of(FrameworkEnum.values()).forEach(e -> has.set(e.getValue().equalsIgnoreCase(framework)));
        return has.get() ? framework : DUBBO.getValue();
    }
}
