package com.atinbo.mvc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * mvc扩展包
 * @author breggor
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = AtinboMvcConfig.class)
public class AtinboMvcConfig {

}