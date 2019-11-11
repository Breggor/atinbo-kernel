package com.atinbo.generate.config;

/**
 * @author zenghao
 * @date 2019-11-11
 */
public class RequestThread {

    private static final ThreadLocal<GenerateConfig> THREAD_LOCAL = new ThreadLocal<>();

    public static void setConfig(GenerateConfig config){
        THREAD_LOCAL.set(config);
    }

    public static void clean() {
        THREAD_LOCAL.remove();
    }

    public static GenerateConfig getConfig(){
        return THREAD_LOCAL.get();
    }
}
