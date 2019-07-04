//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Plugins {
    private final List<IPlugin> pluginList = new ArrayList();

    public Plugins() {
    }

    public Plugins add(IPlugin plugin) {
        this.pluginList.add(plugin);
        return this;
    }

    public void startPlugin() {
        System.out.println("plugins starting...");
        Iterator var1 = this.pluginList.iterator();

        while (var1.hasNext()) {
            IPlugin plugin = (IPlugin) var1.next();
            plugin.start();
        }

    }

    public void stopPlugin() {
        Collections.reverse(this.pluginList);
        System.out.println("plugins in the stop...");
        Iterator var1 = this.pluginList.iterator();

        while (var1.hasNext()) {
            IPlugin plugin = (IPlugin) var1.next();
            plugin.stop();
        }

    }
}
