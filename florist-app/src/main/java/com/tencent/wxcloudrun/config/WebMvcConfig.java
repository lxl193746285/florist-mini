package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")  // 拦截所有/api/**接口
                .excludePathPatterns(
                        "/api/auth/login",           // 登录接口
                        "/api/auth/register/**",     // 注册接口
                        "/api/auth/captcha",         // 验证码接口
                        "/api/auth/forgot-password", // 忘记密码
                        "/api/auth/reset-password",  // 重置密码
                        "/api/auth/wechat/**",       // 微信登录
                        "/api/count"                 // 测试接口
                );
    }
}
