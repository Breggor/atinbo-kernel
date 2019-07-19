//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.boot.starter;

import org.springframework.boot.SpringApplication;

public interface AppStarter {

    static void runApplication(DefaultAppStarter configBoot, String[] args) {
        DefaultAppStarter.CONFIG_BOOT = configBoot;
        DefaultAppStarter.CONFIG_BOOT_CLASSES = configBoot.getClass();
        SpringApplication.run(DefaultAppStarter.CONFIG_BOOT_CLASSES, args);
    }

    void runApplication(String[] var1);
}
