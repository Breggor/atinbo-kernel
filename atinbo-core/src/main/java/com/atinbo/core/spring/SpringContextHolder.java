package com.atinbo.core.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * <pre>
 * 使用说明：
 * 在spring配置文件配置一下，即可使用，不建议使用注解，避免没有scan到包路径.
 * 特别提醒：下面配置必须放在spring配置文件第一行，否则无效.
 * 在具体项目配置如下：
 *     <bean  class="SpringContextHolder"/>
 * </pre>
 *
 * @author Breggor
 */
@Slf4j
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        assertApplicationContextInjected();
        return applicationContext;
    }

    public static <T> T getBean(String name) {
        assertApplicationContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBeanByType(Class requiredType) {
        assertApplicationContextInjected();
        return (T) applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        assertApplicationContextInjected();
        return (T) applicationContext.getBean(beanName, clazz);
    }

    public static void publishEvent(ApplicationEvent event) {
        assertApplicationContextInjected();
        applicationContext.publishEvent(event);
    }

    private static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext:{}", applicationContext);
        applicationContext = null;
    }

    private static void assertApplicationContextInjected() {
        Objects.requireNonNull(applicationContext, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        log.debug("注入ApplicationContext:{}", applicationContext);
        if (SpringContextHolder.applicationContext == null) {
            SpringContextHolder.applicationContext = applicationContext;
        }
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    @SneakyThrows
    public void destroy() {
        SpringContextHolder.clearHolder();
    }
}