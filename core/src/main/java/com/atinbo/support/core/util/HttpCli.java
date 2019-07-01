package com.atinbo.support.core.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class HttpCli {

    private static final int connTimeout = 10000;
    private static final int readTimeout = 10000;
    private static final String charset = "UTF-8";
    private static HttpClient client;

    static {
        //需要通过以下代码声明对https连接支持
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom().loadTrustMaterial(null,
                    new TrustSelfSignedStrategy()).build();
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;


        SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslsf)
                        .build();

        //初始化连接管理器
        //PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        PoolingHttpClientConnectionManager cm =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        //Increase max total connection to 200
        cm.setMaxTotal(200);
        //Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        //使用连接池创建连接
        client = org.apache.http.impl.client.HttpClients.custom().setConnectionManager(cm).build();
    }


    public static byte[] get(String url, Integer connTimeout, Integer readTimeout) throws IOException {
        HttpClient client = HttpCli.client;
        HttpGet action = new HttpGet(url);
        byte[] result;
        try {
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            action.setConfig(customReqConf.build());

            HttpResponse resp = client.execute(action);
            result = IOUtils.toByteArray(resp.getEntity().getContent());
        } finally {
            action.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        try {
            byte[] img = HttpCli.get("https://Img.iblimg.com/invoice-2/2016/12/110533170.pdf", 10000, 1000);
            System.out.println("字节数组长度：" + img.length);
            FileUtils.writeByteArrayToFile(new File("img.pdf"), img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}