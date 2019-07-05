package com.atinbo.support.core.util.http;

import java.util.Map;

public abstract class BaseHttpRequest implements IHttpRequest {

    protected static final String SERVLET_POST = "POST";
    protected static final String SERVLET_GET = "GET";
    protected static final String SERVLET_DELETE = "DELETE";
    protected static final String SERVLET_PUT = "PUT";

    protected HttpHeader header;

    public void sendPost(String urlStr, Map<String, Object> params, Map<String, String> headerMap, HttpCallBack callBack) {
        if (headerMap == null) {
            header = new HttpHeader();
        } else {
            header = new HttpHeader(headerMap);
        }

        addDefaultHeaders();
        sendPost(urlStr, params, callBack);
    }


    protected abstract void sendPost(String urlStr, Map<String, Object> params, HttpCallBack callBack);

    public void sendGet(String urlStr, Map<String, Object> params, Map<String, String> headerMap, HttpCallBack callBack) {
        if (headerMap == null) {
            header = new HttpHeader();
        } else {
            header = new HttpHeader(headerMap);
        }

        addDefaultHeaders();

        sendGet(urlStr, params, callBack);
    }

    protected abstract void sendGet(String urlStr, Map<String, Object> params, HttpCallBack callBack);

    private void addDefaultHeaders() {
        header.addHeader("Appkey", "12343");
        header.addHeader("Udid", "");
        header.addHeader("Os", "pc");
        header.addHeader("Osversion", "");
        header.addHeader("Appversion", "");
        header.addHeader("Sourceid", "");
        header.addHeader("Ver", "");
        header.addHeader("Userid", "123");
        header.addHeader("Usersession", "122333");
        header.addHeader("Content-Type", "text/plain; charset=utf-8");
        header.addHeader("enterpriseStr", "3F7FQ6FQ2F");
        header.addHeader("timespan", "20160713174011");
        header.addHeader("md5hex", "cc6b788f2c9a360daaa83d16c7b93422");
    }
}