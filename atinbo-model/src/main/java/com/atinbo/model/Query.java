package com.atinbo.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询方式 加上注解的属性为查询字段
 *
 * @author zenghao
 * @date 2019-07-20
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /**
     * 查询方式 默认为全匹配
     *
     * @return
     */
    Operator operator() default Operator.EQ;

    /**
     * 查询字段名 不填写默认为注解属性名
     *
     * @return
     */
    String field() default "";

    /**
     * 是否忽略该字段 默认不忽略
     *
     * @return
     */
    boolean ignore() default false;

}
