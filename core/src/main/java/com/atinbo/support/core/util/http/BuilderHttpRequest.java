package com.atinbo.support.core.util.http;

import java.util.HashMap;
import java.util.Map;

public class BuilderHttpRequest {

    private static Map<String, IHttpRequest> httpRequestMap;
    //private static Properties properties;

    static {
        httpRequestMap = new HashMap<String, IHttpRequest>();

        //取消从配置文件加载
        /*
        properties = new Properties();
        try {
            InputStream resourceAsStream = BuilderHttpRequest.class.getClassLoader().getResourceAsStream("assets/config.properties");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }


    public static IHttpRequest createObj(HttpMethodType httpMethodType) {

        //取消从配置文件加载
        //String className = properties.getProperty("BasePack") + "." + properties.getProperty(httpMethodType.getType());
        String className = "com.clr.common.ys.http.UrlConnectionHttpRequest";

        IHttpRequest httpRequest = httpRequestMap.get(className);

        if (httpRequest == null) {
            try {
                httpRequest = (IHttpRequest) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (httpRequest != null) {
            httpRequestMap.put(className, httpRequest);
        }

        return httpRequest;
    }
}
