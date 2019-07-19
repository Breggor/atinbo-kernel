package com.atinbo.boot.plugin;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 插件工厂
 *
 * @author breggor
 */
@Slf4j
public class PluginFactory {
    private final List<Plugin> allPlugins = new CopyOnWriteArrayList<>();


    public void add(Plugin plugin) {
        allPlugins.add(plugin);
    }

    public void start() {
        log.info("start plugins.........");

        allPlugins.forEach(p -> {
            p.start();
        });
    }

    public void stop() {
        Collections.reverse(allPlugins);
        System.out.println("stop plugins..........");

        allPlugins.forEach(p -> {
            p.stop();
        });
    }
}