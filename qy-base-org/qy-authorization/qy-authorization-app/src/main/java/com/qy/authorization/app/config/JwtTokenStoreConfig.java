package com.qy.authorization.app.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.net.MalformedURLException;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

/**
 * jwtToken存储配置
 *
 * @author legendjw
 */
@Configuration
public class JwtTokenStoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenStoreConfig.class);
    @Value("${qy.jwt.key-id}")
    public String keyId;
    @Value("${qy.jwt.jks-path}")
    public String jksPath;
    @Value("${qy.jwt.jks-password}")
    public String jksPassword;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        Map<String, String> customHeaders = Collections.singletonMap("kid", keyId);
        return new CustomJwtAccessTokenConverter(customHeaders, getKeyPair());
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) getKeyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(keyId);
        return new JWKSet(builder.build());
    }

    private KeyPair getKeyPair() {
        FileUrlResource ksFile = null;
        try {
            ksFile = new FileUrlResource(jksPath);
        } catch (MalformedURLException e) {
            logger.error("加载jks证书失败", e);
        }
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, jksPassword.toCharArray());
        return ksFactory.getKeyPair(keyId);
    }
}
