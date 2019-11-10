package com.atinbo.starter;

import com.atinbo.Interceptor.InterceptorChain;
import com.atinbo.config.AppConfig;
import com.atinbo.config.AppEnv;
import com.atinbo.config.CompConfig;
import com.atinbo.handler.HandlerChain;
import com.atinbo.plugin.PluginFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

@ComponentScan(basePackages = {"com.atinbo.boot"})
public abstract class DefaultAppStarter extends Starter implements AppStarter, AppConfig {
    public static Class<? extends AppStarter> CONFIG_BOOT_CLASSES;
    public static DefaultAppStarter CONFIG_BOOT;

    public DefaultAppStarter() {
        this.configEnv(AppConfig.APP_ENV);
    }

    @Override
    public void runApplication(String[] args) {
        String[] cmd = new String[1];
        StringBuilder cmds = new StringBuilder();
        AppEnv appEnv = AppConfig.APP_ENV;

        cmd[0] = cmds.append("--spring.profiles.active").append("=").append(AppConfig.APP_ENV.getEnv()).toString();
        String[] custom = cmd;
        String[] runerArgs = (String[]) Arrays.copyOf(args, args.length, String[].class);

        Arrays.stream(runerArgs).forEach((v) -> {
            String[] arg = v.split("=");
            if (arg.length > 1) {
                System.setProperty(arg[0].replace("--" , ""), arg[1]);
            }

        });

        AppStarter.runApplication(this, runerArgs);
    }

    @Override
    public void configEnv(AppEnv env) {

    }

    @Override
    public void configHandler(HandlerChain handlers) {
    }

    @Override
    public void configInterceptor(InterceptorChain interceptors) {
    }

    @Override
    public void configPlugin(PluginFactory plugins) {
    }

    @Override
    public void configComponent(List<CompConfig> components) {
    }

    @Override
    public void init(BeanFactory applicationContext) {
    }
}