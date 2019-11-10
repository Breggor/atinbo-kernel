package com.atinbo.rocketmq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * RocketMQ生产者自动装配注解
 *
 * @author breggor
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQProducer {
    /**
     * 消息topic
     *
     * @return
     */
    String topic() default "" ;

    /**
     * tag标签
     *
     * @return
     */
    String tag() default "*" ;
}
