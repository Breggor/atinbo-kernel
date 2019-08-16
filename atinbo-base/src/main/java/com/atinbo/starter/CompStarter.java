//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.starter;


import com.atinbo.Interceptor.Interceptor;
import com.atinbo.config.CompConfig;
import com.atinbo.config.DefaultAppConfig;
import com.atinbo.handler.Handler;
import com.atinbo.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CompStarter extends Starter implements CompConfig {
    private List<Handler> componentHandlers = new ArrayList();
    private List<Plugin> componentPlugins = new ArrayList();
    private List<Interceptor> componentInterceptors = new ArrayList();

    public CompStarter() {
    }


    @Override
    public void assemblyHandler() {
        this.configHandler(this.componentHandlers);
        DefaultAppConfig.INSTANCE.attachHandler(this.componentHandlers);
    }

    @Override
    public void assemblyInterceptor() {
        this.configInterceptor(this.componentInterceptors);
        DefaultAppConfig.INSTANCE.attachInterceptor(this.componentInterceptors);
    }

    @Override
    public void assemblyPlugin() {
        this.configPlugin(this.componentPlugins);
        DefaultAppConfig.INSTANCE.attachPlugin(this.componentPlugins);
    }

    public List<Handler> getComponentHandlers() {
        return this.componentHandlers;
    }

    public CompStarter setComponentHandlers(List<Handler> componentHandlers) {
        this.componentHandlers = componentHandlers;
        return this;
    }

    public List<Plugin> getComponentPlugins() {
        return this.componentPlugins;
    }

    public CompStarter setComponentPlugins(List<Plugin> componentPlugins) {
        this.componentPlugins = componentPlugins;
        return this;
    }

    public List<Interceptor> getComponentInterceptors() {
        return this.componentInterceptors;
    }

    public CompStarter setComponentInterceptors(List<Interceptor> componentInterceptors) {
        this.componentInterceptors = componentInterceptors;
        return this;
    }
}
