//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import com.kuaicto.framework.config.IComponentConfig;
import com.kuaicto.framework.core.CoreBoot;
import com.kuaicto.framework.core.Handler;
import com.kuaicto.framework.core.IPlugin;
import com.kuaicto.framework.core.Interceptor;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentStarter extends Starter implements IComponentConfig {
    private List<Handler> componentHandlers = new ArrayList();
    private List<IPlugin> componentPlugins = new ArrayList();
    private List<Interceptor> componentInterceptors = new ArrayList();

    public ComponentStarter() {
    }

    public void assemblyHandler() {
        this.configHandler(this.componentHandlers);
        CoreBoot.me.attachHandler(this.componentHandlers);
    }

    public void assemblyInterceptor() {
        this.configInterceptor(this.componentInterceptors);
        CoreBoot.me.attachInterceptor(this.componentInterceptors);
    }

    public void assemblyPlugin() {
        this.configPlugin(this.componentPlugins);
        CoreBoot.me.attachPlugin(this.componentPlugins);
    }

    public List<Handler> getComponentHandlers() {
        return this.componentHandlers;
    }

    public ComponentStarter setComponentHandlers(List<Handler> componentHandlers) {
        this.componentHandlers = componentHandlers;
        return this;
    }

    public List<IPlugin> getComponentPlugins() {
        return this.componentPlugins;
    }

    public ComponentStarter setComponentPlugins(List<IPlugin> componentPlugins) {
        this.componentPlugins = componentPlugins;
        return this;
    }

    public List<Interceptor> getComponentInterceptors() {
        return this.componentInterceptors;
    }

    public ComponentStarter setComponentInterceptors(List<Interceptor> componentInterceptors) {
        this.componentInterceptors = componentInterceptors;
        return this;
    }
}
