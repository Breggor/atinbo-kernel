package com.atinbo.rocketmq.annotation;

import java.lang.annotation.*;


/**
 * @author breggor
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableRocketMQ {
}
