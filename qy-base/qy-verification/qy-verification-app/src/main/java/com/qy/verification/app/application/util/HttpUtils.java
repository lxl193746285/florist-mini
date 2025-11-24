package com.qy.verification.app.application.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtils {

    /**
     * 发送http请求
     *
     * @param request 请求
     * @return
     */
    public static String httpClient(HttpUriRequest request) throws Exception {
        //设置忽略ssl证书验证
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setSSLSocketFactory(sslSocketFactory);
        HttpClient httpClient = builder.build();

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();
        return EntityUtils.toString(responseEntity);
    }

    /**
     * 发送post,json请求
     *
     * @param url     请求地址
     * @param body    请求体
     * @param headers
     * @return
     */
    public static String postJson(String url, JSONObject body, Map<String, String> headers) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        //设置请求头
        httpPost.setHeader("Content-Type", "application/json");
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }

        //设置请求体
        StringEntity entity = new StringEntity(body.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(entity);

        return httpClient(httpPost);
    }
}
