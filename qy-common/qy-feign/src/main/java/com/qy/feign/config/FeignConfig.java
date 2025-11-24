package com.qy.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置
 *
 * @author legendjw
 */
@Configuration
public class FeignConfig {
    //@Bean
    //Logger.Level feignLoggerLevel() {
    //    return Logger.Level.FULL;
    //}

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
