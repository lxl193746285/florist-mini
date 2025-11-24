package com.qy.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/v4/api/**").permitAll()
                .antMatchers("/v4/verification/**").permitAll()
                .antMatchers("/v4/attachment/configs",
                        "/v4/attachment/local/**",
                        "/v4/attachment/aliyun-oss/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/v4/identity/accounts/**").permitAll()
                .antMatchers("/v4/organization/invitations/info",
                        "/v4/organization/wechat/wxuser",
                        "/v4/organization/weixin",
                        "/v4/system/qrcode-model/qrcode").permitAll()
                .antMatchers("/.well-known/jwks.json").permitAll()
                .antMatchers("/v4/region/areas/app").permitAll()
                .antMatchers("/v2/**",
                        "/swagger-ui.html",
                        "/swagger-ui.html/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                .antMatchers("/v4/system/app-versions/latest").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v4/identity/accounts/**")
                .antMatchers("/v4/organization/invitations/info")
                .antMatchers("/v4/organization/wechat/wxuser")
                .antMatchers("/v4/organization/weixin");
    }
}