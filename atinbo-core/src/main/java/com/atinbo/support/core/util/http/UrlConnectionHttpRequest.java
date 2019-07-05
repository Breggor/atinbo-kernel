package com.atinbo.support.core.util.http;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UrlConnectionHttpRequest extends BaseHttpRequest implements IHttpRequest {

    public void sendGet(String urlStr, Map<String, Object> params, HttpCallBack callBack) {
        HttpURLConnection conn = null;
        try {
            String paramStr = prepareParam(params);
            System.out.println("paramStr == " + paramStr);
            if (paramStr.trim().length() > 0) {
                urlStr += "?" + paramStr;
            }
            URL url = new URL(urlStr);
            System.out.println(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(SERVLET_GET);

            setHeaders(conn);

            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String result = "";
            while ((line = br.readLine()) != null) {
                result += "/n" + line;
            }
            System.out.println(result);
            if (callBack != null) {
                callBack.onResponse(result);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }
    }

    public void sendPost(String urlStr, Map<String, Object> params, HttpCallBack callBack) {
        HttpURLConnection conn = null;

        try {
            // 获取连接
            URL url = new URL(urlStr);
            System.out.println(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            // 设置默认配置
            conn.setRequestMethod(SERVLET_POST);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setDoInput(true);   // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoOutput(true);   // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setUseCaches(false);  // Post 请求不能使用缓存

            // 设置请求头
            setHeaders(conn);
            String method = (String) params.get("method");
            conn.addRequestProperty("X-JSON-RPC", method);
            System.out.println("headers == " + conn.getRequestProperties().toString());

            // 设置参数
            int requestID = UUID.randomUUID().hashCode();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", requestID);
            map.put("jsonrpc", "2.0");
            map.put("method", method);
            map.put("params", new JSONObject(params));

            JSONObject jsobj = new JSONObject(map);
            System.out.println("params == " + jsobj.toString());

            // 开始请求
            conn.connect();

            // 输出参数
            OutputStream os = conn.getOutputStream();
            os.write(jsobj.toString().getBytes("utf-8"));
            os.close();

            // 读取结果
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }

//            Logger.i("result == " + result);
            if (callBack != null) {
                callBack.onResponse(result);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.ErrorListener("连接错误！", 0);
            }

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


    }

    private String prepareParam(Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        if (paramMap.isEmpty()) {
            return "";
        } else {
            for (String key : paramMap.keySet()) {
                String value = (String) paramMap.get(key);
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }

    private void setHeaders(HttpURLConnection conn) {
        Map<String, String> headerMap = header.getHeaderMap();

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            conn.addRequestProperty(entry.getKey(), entry.getValue());
        }

    }
}
