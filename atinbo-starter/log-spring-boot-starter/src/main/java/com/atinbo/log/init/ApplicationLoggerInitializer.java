package com.atinbo.log.init;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author zenghao
 * @date 2019-07-23
 * 通过环境变量的形式注入 logging.file
 * 自动维护 Spring Boot Admin Logger Viewer
 */
public class ApplicationLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        String appName = applicationContext.getApplicationName();
        String logBase = environment.getProperty("sys.log.path", "logs");
        String logName = environment.getProperty("sys.log.file", "debug.log");
        // spring boot admin 直接加载日志
        System.setProperty("logging.file", String.format("%s/%s/%s", logBase, appName, logName));
    }
}
