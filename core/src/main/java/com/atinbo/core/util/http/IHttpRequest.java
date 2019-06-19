package com.atinbo.core.util.http;

import java.util.Map;

public interface IHttpRequest {

    void sendGet(String urlStr, Map<String, Object> params, Map<String, String> headers, HttpCallBack callBack);

    void sendPost(String urlStr, Map<String, Object> params, Map<String, String> headers, HttpCallBack callBack);

}