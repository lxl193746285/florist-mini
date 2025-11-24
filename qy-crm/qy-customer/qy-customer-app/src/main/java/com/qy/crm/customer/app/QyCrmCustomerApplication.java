package com.qy.crm.customer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.qy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qy")
@RemoteApplicationEventScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class QyCrmCustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(QyCrmCustomerApplication.class, args);
    }
}