package com.qy.tool.interfaces;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.qy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qy")
@MapperScan(value = "com.qy.**.mapper")
public class QyToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(QyToolApplication.class, args);
    }
}