//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import com.kuaicto.framework.core.Handler;
import com.kuaicto.framework.core.IPlugin;
import com.kuaicto.framework.core.Interceptor;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;

public interface IComponentConfig extends Serializable {
    default void assemblyComponent() {
        this.assemblyHandler();
        this.assemblyInterceptor();
        this.assemblyPlugin();
    }

    void assemblyHandler();

    void assemblyInterceptor();

    void assemblyPlugin();

    void init(BeanFactory var1);

    void configHandler(List<Handler> var1);

    void configInterceptor(List<Interceptor> var1);

    void configPlugin(List<IPlugin> var1);
}
