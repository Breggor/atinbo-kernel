package com.atinbo.support.core.framework.spring;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

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
@Component
public class SpringContextHolder implements ApplicationContextAware, EmbeddedValueResolverAware, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;

    private static StringValueResolver stringValueResolver = null;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertApplicationContextInjected();
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext:{}", applicationContext);
        if (SpringContextHolder.applicationContext == null) {
            SpringContextHolder.applicationContext = applicationContext;
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertApplicationContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanByType(Class beanClazz) {
        assertApplicationContextInjected();
        return (T) applicationContext.getBean(beanClazz);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        logger.debug("清除SpringContextHolder中的ApplicationContext:{}", applicationContext);
        applicationContext = null;
        stringValueResolver = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    public static void assertApplicationContextInjected() {
        Validate.validState(applicationContext != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    }

    /**
     * 检查stringValueResolver不为空.
     */
    public static void assertStringValueInjected() {
        Validate.validState(stringValueResolver != null, "stringValueResolver属性未注入");
    }

    /**
     * 从静态变量applicationContext中取得property, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(String name) {
        assertApplicationContextInjected();
        assertStringValueInjected();
        return (T) stringValueResolver.resolveStringValue(String.format("${%s}", name));
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    /**
     * 实现EmbeddedValueResolverAware接口，将配置文件中的配置信息保存
     *
     * @param stringValueResolver
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        logger.debug("注入stringValueResolver:{}", stringValueResolver);
        if (SpringContextHolder.stringValueResolver == null) {
            SpringContextHolder.stringValueResolver = stringValueResolver;
        }
    }
}


