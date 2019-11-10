//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.webmvc.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 原型rest
 *
 * @author breggor
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Scope("prototype")
public @interface PrototypeRestController {


    @AliasFor(annotation = RestController.class)
    String value() default "" ;
}
