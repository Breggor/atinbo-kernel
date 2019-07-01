package com.atinbo.support.core.util.http;

public interface HttpCallBack {

    void onResponse(String response);

    void ErrorListener(String errorMessage, int errorCode);

}
