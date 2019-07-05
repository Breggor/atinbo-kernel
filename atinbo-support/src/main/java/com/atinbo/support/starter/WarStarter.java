//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

public abstract class WarStarter extends SpringBootServletInitializer {
    public WarStarter() {
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        ApplicationStarter.CONFIG_BOOT_CLASSES = this.configBootClass();
        builder.sources(new Class[]{ApplicationStarter.CONFIG_BOOT_CLASSES});
        return super.configure(builder);
    }

    protected abstract Class<? extends ApplicationStarter> configBootClass();

    @Override
    protected WebApplicationContext run(SpringApplication application) {
        String[] custom = new String[]{"--spring.profiles.active=dev"};
        return (WebApplicationContext) application.run(custom);
    }
}
