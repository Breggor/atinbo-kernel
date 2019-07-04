//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import com.kuaicto.framework.core.Constant;
import com.kuaicto.framework.core.Handlers;
import com.kuaicto.framework.core.Interceptors;
import com.kuaicto.framework.core.Plugins;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;

public interface IApplicationConfig extends Serializable {
    Constant GLOBAL_CONSTANT = new Constant();

    void configConsts(Constant var1);

    void configHandler(Handlers var1);

    void configInterceptor(Interceptors var1);

    void configPlugin(Plugins var1);

    void configComponent(List<IComponentConfig> var1);

    void init(BeanFactory var1);
}
