//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

@ComponentScan(
        basePackages = {"com.atinbo.support"}
)
public abstract class ApplicationStarter extends Starter implements IApplicationStarter, IApplicationConfig {
    public static Class<? extends ApplicationStarter> CONFIG_BOOT_CLASSES;
    public static ApplicationStarter CONFIG_BOOT;

    public ApplicationStarter() {
        this.configConsts(IApplicationConfig.GLOBAL_CONSTANT);
    }

    @Override
    public void runApplication(String[] args) {
        String[] var10000 = new String[1];
        StringBuilder var10003 = new StringBuilder();
        Constant var10004 = IApplicationConfig.GLOBAL_CONSTANT;
        var10000[0] = var10003.append("--spring.profiles.active").append("=").append(IApplicationConfig.GLOBAL_CONSTANT.getEnv()).toString();
        String[] custom = var10000;
        String[] runerArgs = (String[]) Arrays.copyOf(args, args.length, String[].class);
        Arrays.stream(runerArgs).forEach((v) -> {
            String[] arg = v.split("=");
            if (arg.length > 1) {
                System.setProperty(arg[0].replace("--", ""), arg[1]);
            }

        });
        IApplicationStarter.runApplication(this, runerArgs);
    }

    @Override
    public void configConsts(Constant constant) {
    }

    @Override
    public void configHandler(Handlers handlers) {
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
    }

    @Override
    public void configPlugin(Plugins plugins) {
    }

    @Override
    public void configComponent(List<IComponentConfig> components) {
    }

    @Override
    public void init(BeanFactory applicationContext) {
    }
}
