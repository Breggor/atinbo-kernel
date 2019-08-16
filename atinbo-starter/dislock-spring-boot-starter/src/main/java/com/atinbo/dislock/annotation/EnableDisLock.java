package com.atinbo.dislock.annotation;


import java.lang.annotation.*;

/**
 * 开启分布式Lock
 *
 * @author breggor
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableDisLock {
}
