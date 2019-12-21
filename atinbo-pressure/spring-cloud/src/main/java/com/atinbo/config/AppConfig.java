package com.atinbo.config;


import com.atinbo.Interceptor.InterceptorChain;
import com.atinbo.handler.HandlerChain;
import com.atinbo.plugin.PluginFactory;
import org.springframework.beans.factory.BeanFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 应用配置
 *
 * @author breggor
 */
public interface AppConfig extends Serializable {
    AppEnv APP_ENV = new AppEnv();

    void init(BeanFactory beanFactory);

    void configEnv(AppEnv env);

    void configHandler(HandlerChain chain);

    void configInterceptor(InterceptorChain chain);

    void configPlugin(PluginFactory pluginFactory);

    void configComponent(List<CompConfig> cmpConfig);

}
