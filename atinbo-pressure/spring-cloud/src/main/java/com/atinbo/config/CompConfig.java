package com.atinbo.config;


import com.atinbo.Interceptor.Interceptor;
import com.atinbo.handler.Handler;
import com.atinbo.plugin.Plugin;
import org.springframework.beans.factory.BeanFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 组件配置
 *
 * @author breggor
 */
public interface CompConfig extends Serializable {

    default void assembly() {
        this.assemblyHandler();
        this.assemblyInterceptor();
        this.assemblyPlugin();
    }

    void assemblyHandler();

    void assemblyInterceptor();

    void assemblyPlugin();

    void init(BeanFactory beanFactory);

    void configHandler(List<Handler> handlers);

    void configInterceptor(List<Interceptor> interceptors);

    void configPlugin(List<Plugin> plugins);
}
