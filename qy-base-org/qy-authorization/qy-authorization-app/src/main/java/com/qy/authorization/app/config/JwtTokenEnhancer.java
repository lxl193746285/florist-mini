package com.qy.authorization.app.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jwt内容增强器
 *
 * @author legendjw
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2Request oAuth2Request = authentication.getOAuth2Request();
        Map<String, Object> info = new HashMap<>();
        Map<String, String> requestParameters = oAuth2Request.isRefresh() ?
                oAuth2Request.getRefreshTokenRequest().getRequestParameters() :
                oAuth2Request.getRequestParameters();
        List<String> ignoreParameters = Arrays.asList("grant_type", "refresh_token");
        for (String s : requestParameters.keySet()) {
            if (!ignoreParameters.contains(s)) {
                info.put(s, requestParameters.get(s));
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}