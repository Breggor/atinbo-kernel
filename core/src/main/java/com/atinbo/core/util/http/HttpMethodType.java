package com.atinbo.core.util.http;

public enum HttpMethodType {

    URLCONNECTION("urlconnection"),
    OKHTTP("okhttp");

    private String type;

    HttpMethodType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
