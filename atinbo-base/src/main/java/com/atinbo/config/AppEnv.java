//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.config;


import com.atinbo.utils.LogUtils;

public class AppEnv {
    public static final String SPRING_ENV_CMD = "--spring.profiles.active";
    private String env = "dev";
    private Boolean debug = false;
    private String logFilePath;
    private String logMaxHistory;

    public AppEnv() {
        this.setLogFilePath(System.getProperty("user.dir"));
        this.setLogMaxHistory("3");
    }

    public static String getSpringEnvCmd() {
        return "--spring.profiles.active";
    }

    public String getEnv() {
        return this.env;
    }

    public AppEnv setEnv(String env) {
        this.env = env;
        return this;
    }

    public Boolean getDebug() {
        return this.debug;
    }

    public AppEnv setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getLogFilePath() {
        return this.logFilePath;
    }

    public AppEnv setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath == null ? null : logFilePath.trim();
        LogUtils.pushLogFilePath(this.logFilePath);
        return this;
    }

    public AppEnv setLogMaxHistory(String logMaxHistory) {
        this.logMaxHistory = logMaxHistory == null ? null : logMaxHistory.trim();
        LogUtils.pushLogMaxHistory(this.logMaxHistory);
        return this;
    }
}
