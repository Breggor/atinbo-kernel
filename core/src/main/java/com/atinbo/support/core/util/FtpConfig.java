package com.atinbo.support.core.util;

import java.io.Serializable;

public class FtpConfig implements Serializable {

    private String ftpUser;
    private String ftpPwd;
    private String ftpIp;
    private String ftpHead;
    private String ftpPort;

    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpHead() {
        return ftpHead;
    }

    public void setFtpHead(String ftpHead) {
        this.ftpHead = ftpHead;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }


}
