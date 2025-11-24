package com.qy.base.config;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class DataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("/v4/api/attachment") || request.getRequestURI().contains("/v4/attachment")){
            DynamicDataSourceContextHolder.push("platform");
            return true;
        }
        if (request.getRequestURI().contains("/v4/api/region/areas") || request.getRequestURI().contains("/v4/region/areas")){
            DynamicDataSourceContextHolder.push("platform");
            return true;
        }
        if (request.getRequestURI().contains("/v4/api/code-table") || request.getRequestURI().contains("/v4/code-table/")){
            DynamicDataSourceContextHolder.push("arkcsd");
            return true;
        }
        if (request.getRequestURI().contains("/v4/api/verification/") || request.getRequestURI().contains("/v4/verification/")){
            DynamicDataSourceContextHolder.push("platform");
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null){
            return true;
        }
        token = token.replace("Bearer ", "");
        SignedJWT sjwt = SignedJWT.parse(token);
        Map<String, Object> payload = sjwt.getPayload().toJSONObject();
        if (payload.get("datasource") != null && !payload.get("datasource").toString().isEmpty()) {
            DynamicDataSourceContextHolder.push(payload.get("datasource").toString());
        }
//        if (request.getRequestURI().contains("/v4/api/system/autonumber")){
//            DynamicDataSourceContextHolder.push("arkcsd");
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        DynamicDataSourceContextHolder.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
