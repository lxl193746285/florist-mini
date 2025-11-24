package com.qy.rest.config;

import org.apache.commons.text.CaseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求参数下划线转驼峰法配置
 *
 * @author legendjw
 */
@Configuration
public class SnakeCaseRequestParamConfig {
    @Bean
    public OncePerRequestFilter snakeCaseConverterFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                if (!request.getMethod().equals("GET")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                final Map<String, String[]> parameters = new ConcurrentHashMap<>();

                for (String param : request.getParameterMap().keySet()) {
                    String camelCaseParam = CaseUtils.toCamelCase(param, false, new char[]{'_'});

                    String[] value = request.getParameterValues(param);
                    parameters.put(camelCaseParam, value);
                    parameters.put(param, value);
                }

                filterChain.doFilter(new HttpServletRequestWrapper(request) {
                    @Override
                    public String getParameter(String name) {
                        return parameters.containsKey(name) ? parameters.get(name)[0] : null;
                    }

                    @Override
                    public Enumeration<String> getParameterNames() {
                        return Collections.enumeration(parameters.keySet());
                    }

                    @Override
                    public String[] getParameterValues(String name) {
                        return parameters.get(name);
                    }

                    @Override
                    public Map<String, String[]> getParameterMap() {
                        return parameters;
                    }
                }, response);
            }
        };
    }
}