package com.atinbo.core.util.http;

import java.util.Map;

public class HttpHelper {
    private static HttpMethodType defaultHttpMethodType = HttpMethodType.URLCONNECTION;

    public static void sendGet(String url, Map<String, Object> params, HttpCallBack callBack) {
        HttpHelper.sendGet(defaultHttpMethodType, url, params, null, callBack);
    }

    public static void sendGet(HttpMethodType httpMethodType, String url, Map<String, Object> params, HttpCallBack callBack) {
        HttpHelper.sendGet(httpMethodType, url, params, null, callBack);
    }

    public static void sendGet(String url, Map<String, Object> params, Map<String, String> headers, HttpCallBack callBack) {
        HttpHelper.sendGet(defaultHttpMethodType, url, params, headers, callBack);
    }

    public static void sendGet(HttpMethodType httpMethodType, String url, Map<String, Object> params, Map<String, String> headers, HttpCallBack callBack) {
        BuilderHttpRequest.createObj(httpMethodType).sendGet(url, params, headers, callBack);
    }

    public static void sendPost(String url, Map<String, Object> params, HttpCallBack callBack) {
        HttpHelper.sendPost(defaultHttpMethodType, url, params, null, callBack);
    }

    public static void sendPost(HttpMethodType httpMethodType, String url, Map<String, Object> params, HttpCallBack callBack) {
        HttpHelper.sendPost(httpMethodType, url, params, null, callBack);
    }

    public static void sendPost(String url, Map<String, Object> params, Map<String, String> headers, HttpCallBack callBack) {
        HttpHelper.sendPost(defaultHttpMethodType, url, params, headers, callBack);
    }

    public static void sendPost(HttpMethodType httpMethodType, String url, Map<String, Object> params, Map<String, String> headers, HttpCallBack callBack) {
        BuilderHttpRequest.createObj(httpMethodType).sendPost(url, params, headers, callBack);
    }


}
