package com.qy.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addInterceptor 注册拦截器
        //addPathPatterns 表示拦截的控制器
        //excludePathPatterns表示排除的控制器
        registry.addInterceptor(new DataSourceInterceptor());
    }
}
