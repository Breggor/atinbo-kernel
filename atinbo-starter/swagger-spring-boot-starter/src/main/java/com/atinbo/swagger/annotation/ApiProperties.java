package com.atinbo.swagger.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义 ApiModel 中的字段
 *
 * @author zenghao
 * @date 2019-12-10
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiProperties {

    /**
     * 需要的字段
     *
     * @return
     */
    @AliasFor("include")
    String[] value() default {};

    /**
     * 需要的字段
     *
     * @return
     */
    @AliasFor("value")
    String[] include() default {};

    /**
     * 排除的字段
     *
     * @return
     */
    String[] exclude() default {};

    /**
     * form名称
     *
     * @return
     */
    String name();
}
