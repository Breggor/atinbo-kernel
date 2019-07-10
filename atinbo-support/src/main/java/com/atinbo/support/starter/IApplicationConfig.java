//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;


import org.springframework.beans.factory.BeanFactory;

import java.io.Serializable;
import java.util.List;

public interface IApplicationConfig extends Serializable {
    Constant GLOBAL_CONSTANT = new Constant();

    void configConsts(Constant var1);

    void configHandler(Handlers var1);

    void configInterceptor(Interceptors var1);

    void configPlugin(Plugins var1);

    void configComponent(List<IComponentConfig> var1);

    void init(BeanFactory var1);
}
