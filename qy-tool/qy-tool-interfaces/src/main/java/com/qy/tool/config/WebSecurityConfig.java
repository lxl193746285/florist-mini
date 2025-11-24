package com.qy.tool.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/v4/api/**").permitAll()
                .antMatchers("/v4/verification/**").permitAll()
                .antMatchers("/v4/attachment/configs", "/v4/attachment/local/**", "/v4/attachment/aliyun-oss/**",
                        "/v2/**",
                        "/v4/system/interfaces/data/receive/**",
                        "/swagger-ui.html",
                        "/swagger-ui.html/**","/webjars/**","/swagger-resources/**"

                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }
}