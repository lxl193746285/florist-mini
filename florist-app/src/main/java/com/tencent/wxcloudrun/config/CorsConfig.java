package com.tencent.wxcloudrun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * 允许前端应用跨域访问后端接口
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 允许所有来源（开发环境）
        // 生产环境建议指定具体域名，如：config.addAllowedOrigin("https://yourdomain.com");
        config.addAllowedOriginPattern("*");

        // 允许所有请求头
        config.addAllowedHeader("*");

        // 允许所有HTTP方法（GET, POST, PUT, DELETE等）
        config.addAllowedMethod("*");

        // 允许发送Cookie等认证信息
        config.setAllowCredentials(true);

        // 预检请求的有效期（秒）
        config.setMaxAge(3600L);

        // 对所有路径应用此配置
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
