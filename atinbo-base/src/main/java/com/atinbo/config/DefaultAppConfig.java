package com.atinbo.config;

import com.atinbo.Interceptor.Interceptor;
import com.atinbo.Interceptor.InterceptorChain;
import com.atinbo.handler.*;
import com.atinbo.plugin.Plugin;
import com.atinbo.plugin.PluginFactory;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认app配置
 *
 * @author breggor
 */
public class DefaultAppConfig implements AppConfig {
    public static final DefaultAppConfig INSTANCE = new DefaultAppConfig();

    private final List<Handler> handlers = new ArrayList();

    private final List<Plugin> plugins = new ArrayList();

    private final List<Interceptor> interceptors = new ArrayList();

    public DefaultAppConfig() {
        this.configEnv(AppConfig.APP_ENV);
    }

    @Override
    public void init(BeanFactory applicationContext) {
    }

    @Override
    public void configEnv(AppEnv appEnv) {
        appEnv.setEnv("dev");
    }

    @Override
    public void configHandler(HandlerChain chain) {
        chain.add(new LoggerHandler());
        chain.add(new FrameworkExceptionHandler());
        chain.add(new FrameworkDiagnoseDataHandler());
        chain.add(new GatewayMediaFetchHandler());

        this.handlers.forEach(h -> {
            chain.add(h);
        });
    }

    @Override
    public void configInterceptor(InterceptorChain chain) {
//        chain.add(new RequestContextInterceptor());
        this.interceptors.forEach(i -> {
            chain.add(i);
        });
    }

    @Override
    public void configPlugin(PluginFactory pluginFactory) {
        this.plugins.forEach(p -> {
            pluginFactory.add(p);
        });
    }

    @Override
    public void configComponent(List<CompConfig> components) {
    }

    public void attachHandler(Handler handler) {
        this.handlers.add(handler);
    }

    public void attachInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    public void attachPlugin(Plugin plugin) {
        this.plugins.add(plugin);
    }

    public void attachPlugin(List<Plugin> plugins) {
        this.plugins.addAll(plugins);
    }

    public void attachInterceptor(List<Interceptor> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    public void attachHandler(List<Handler> handlers) {
        this.handlers.addAll(handlers);
    }
}
