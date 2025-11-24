package com.qy.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用Feign token请求拦截器
 *
 * @author legendjw
 */
public class FeignTokenRequestInterceptor implements RequestInterceptor {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (request.getHeader(AUTHORIZATION_HEADER) != null) {
            requestTemplate.header(AUTHORIZATION_HEADER, request.getHeader(AUTHORIZATION_HEADER));
        }
    }
}
