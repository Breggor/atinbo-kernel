package com.atinbo.log.annotation;

import java.lang.annotation.*;

/**
 * @author zenghao
 * @date 2019-7-23
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 描述
     *
     * @return {String}
     */
    String value();

    /**
     * 类型
     *
     * @return
     */
    String type() default "default" ;

}
