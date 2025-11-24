package com.qy.message.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;

@Configuration
public class HttpConfig {

    @Value("${qy.forward-proxy.ip}")
    private String ip;
    @Value("${qy.forward-proxy.port}")
    private Integer port;

    @Bean(name = "thirdPartRestTemplate")
    public RestTemplate thirdPartRestTemplate() {
//创建一个RestTemplate 实例
        final RestTemplate restTemplate = new RestTemplate();
//建了一个 SimpleClientHttpRequestFactory 实例，并将其设置为 RestTemplate 的请求工厂
        final SimpleClientHttpRequestFactory reqFactory = new SimpleClientHttpRequestFactory();
//创建一个 Proxy 实例，并传入代理类型和代理服务器的地址与端口，将其设置为 reqFactory 的代理：
        reqFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));

        restTemplate.setRequestFactory(reqFactory);
//设置了一个拦截器 LoggingRequestInterceptor，并将其添加到 RestTemplate 的拦截器列表中：
        restTemplate.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));
        return restTemplate;
    }

}