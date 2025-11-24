package com.qy.member.app.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                .antMatchers("/v4/api/**", "/v4/mbr/accounts/**", "/v4/mbr/weixin/**").permitAll()
                .antMatchers("/v2/**",
                        "/swagger-ui.html",
                        "/swagger-ui.html/**",
                        "/v4/mbr/passport-qrcode/weixin","/v4/mbr/passport-qrcode/check/**", "/v4/mbr/passport-qrcode/login",
                        "/webjars/**","/swagger-resources/**").permitAll()
                .antMatchers("/v4/mbr/member/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/v4/mbr/accounts/**", "/v4/mbr/weixin/**");
    }
}