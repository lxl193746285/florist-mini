//package com.qy.authorization.app.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//@Configuration
//public class RedissionConfig {
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private String port;
//
////    @Value("${spring.redis.password}")
////    private String password;
//
//
//    /*
//     * @Description:    所有对 Redisson的使用都是通过 RedissonClient对象
//     * @Return:         org.redisson.api.RedissonClient
//     **/
//    @Bean(destroyMethod = "shutdown")
//    RedissonClient redisson() throws IOException {
//
//        // 1、创建配置
//        Config config = new Config ( );
//        config.useSingleServer ().setAddress ("redis://" + host + ":" + port )
////                .setPassword(password)
//        ;
//        // 集群模式
//        // config.useClusterServers ( )
//        // .addNodeAddress ("redis://127.0.0.1:7004", "redis://127.0.0.1:7001");
//        // 密码设置
//        //.setPassword ("abc123456");
//
//        // 2、根据 Config 实例创建出 RedissonClient 对象
//        return Redisson.create (config);
//    }
//}
