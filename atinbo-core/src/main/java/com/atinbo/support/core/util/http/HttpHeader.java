package com.atinbo.support.core.util.http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> headerMap;

    public HttpHeader() {
        headerMap = new HashMap<String, String>();
    }

    public HttpHeader(Map<String, String> headerMap) {
        this.headerMap = new HashMap<String, String>();
        this.headerMap.putAll(headerMap);
    }

    public HttpHeader addHeader(String headerKey, String headerValue) {
        headerMap.put(headerKey, headerValue);
        return this;
    }

    public HttpHeader addHeaders(HttpHeader header) {
        headerMap.putAll(header.getHeaderMap());
        return this;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getHeaderString() {
        StringBuffer sb = new StringBuffer();
        if (headerMap.isEmpty()) {
            return "";
        }

        for (String key : headerMap.keySet()) {
            String value = (String) headerMap.get(key);
            if (sb.length() < 1) {
                sb.append(key).append("=").append(value);
            } else {
                sb.append("&").append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }

}
