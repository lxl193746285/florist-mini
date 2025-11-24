package com.qy.authorization.app.application.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.authorization.app.application.command.GenerateAccessTokenCommand;
import com.qy.authorization.app.application.command.RefreshAccessTokenCommand;
import com.qy.authorization.app.application.dto.AccessTokenDTO;
import com.qy.authorization.app.application.dto.ValidateClientResultDTO;
import com.qy.authorization.app.application.service.AuthClientService;
import com.qy.authorization.app.application.service.AuthorizationService;
import com.qy.authorization.app.application.service.SameAccountLoginLimit;
import com.qy.authorization.app.config.CustomJwtAccessTokenConverter;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.rest.exception.UnauthorizedException;
import com.qy.rest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 授权服务实现
 *
 * @author legendjw
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private ClientDetailsService clientDetailsService;
    private OAuth2RequestFactory oAuth2RequestFactory;
    private AuthorizationServerTokenServices tokenServices;
    private PasswordEncoder passwordEncoder;
    private TokenStore tokenStore;
    private JwtAccessTokenConverter jwtTokenEnhancer;
    private SameAccountLoginLimit sameAccountLoginLimit;
    @Value("${qy.enable-same-account-login-limit}")
    private boolean enableSameAccountLoginLimit;
    private RedisTemplate redisTemplate;
    private AuthClientService authClientService;
//    private RedissonClient redissonClient;
    private OrgDatasourceClient orgDatasourceClient;

    public AuthorizationServiceImpl(ClientDetailsService clientDetailsService, OAuth2RequestFactory oAuth2RequestFactory,
                                    AuthorizationServerTokenServices tokenServices, PasswordEncoder passwordEncoder, TokenStore tokenStore,
                                    JwtAccessTokenConverter jwtTokenEnhancer, SameAccountLoginLimit sameAccountLoginLimit, RedisTemplate redisTemplate,
                                    AuthClientService authClientService, OrgDatasourceClient orgDatasourceClient) {
        this.clientDetailsService = clientDetailsService;
        this.oAuth2RequestFactory = oAuth2RequestFactory;
        this.tokenServices = tokenServices;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
        this.sameAccountLoginLimit = sameAccountLoginLimit;
        this.redisTemplate = redisTemplate;
        this.authClientService = authClientService;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    public ValidateClientResultDTO validateClient(String clientId, String clientSecret) {
        DynamicDataSourceContextHolder.peek();
        try {
            ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
            if (!passwordEncoder.matches(clientSecret, authenticatedClient.getClientSecret())) {
                throw new ValidationException("非法的客户端");
            }
        } catch (Exception e) {
            return new ValidateClientResultDTO(false, "非法的客户端");
        }
        return new ValidateClientResultDTO(true);
    }

    @Override
    public AccessTokenDTO generateAccessToken(GenerateAccessTokenCommand command) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("context_id", command.getContextId());
        parameters.put("client_id", command.getClientId());
        parameters.put("user_id", command.getUserId());
        if (command.getExtraData() != null) {
            parameters.putAll(command.getExtraData());
            if (parameters.get("organization_id") != null){
                OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(Long.parseLong(parameters.get("organization_id")));
                if (orgDatasourceDTO != null){
                    parameters.put("datasource", orgDatasourceDTO.getDatasourceName());
                }
            }
        }

        String clientId = command.getClientId();
        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

        TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);

        OAuth2Request storedOAuth2Request = oAuth2RequestFactory.createOAuth2Request(authenticatedClient, tokenRequest);
        OAuth2AccessToken oAuth2AccessToken = tokenServices.createAccessToken(new OAuth2Authentication(storedOAuth2Request, null));
        // sameAccountLoginLimit(oAuth2AccessToken);
        if (command.getExtraData() == null || command.getExtraData().get("member_id") == null) {
            storeToken(command.getUserId(), clientId, oAuth2AccessToken);
        } else {
            storeToken(command.getExtraData().get("member_id"), clientId, oAuth2AccessToken);
        }
        return toTokenDTO(oAuth2AccessToken);
    }

    @Override
    public AccessTokenDTO refreshAccessToken(RefreshAccessTokenCommand command) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("context_id", command.getContextId());
        parameters.put("client_id", command.getClientId());
        parameters.put("refresh_token", command.getRefreshToken());

        //从refresh_token中获取之前保存的参数重新生成新token
        try {
            CustomJwtAccessTokenConverter jwtAccessTokenConverter = (CustomJwtAccessTokenConverter) jwtTokenEnhancer;
            Map<String, Object> tokenParameters = jwtAccessTokenConverter.publicDecode(command.getRefreshToken());
            if (!validateRefreshToken(tokenParameters)) {
                throw new UnauthorizedException("非法的刷新token");
            }

            List<String> ignoreParameters = Arrays.asList("scope", "ati", "exp", "jti", "client_id");
            String memberId = null;
            String userId = null;
            for (String s : tokenParameters.keySet()) {
                if (!ignoreParameters.contains(s)) {
                    parameters.put(s, tokenParameters.get(s).toString());
                }
                if (s.equals("member_id")) {
                    memberId = tokenParameters.get(s).toString();
                }
                if (s.equals("user_id")) {
                    userId = tokenParameters.get(s).toString();
                }
            }
            if (memberId == null) {
                memberId = userId;
            }

            String clientId = command.getClientId();
            ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

            TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);
            OAuth2AccessToken oAuth2AccessToken = tokenServices.refreshAccessToken(command.getRefreshToken(), tokenRequest);
            // sameAccountLoginLimit(oAuth2AccessToken);

            storeToken(memberId, clientId, oAuth2AccessToken);
            return toTokenDTO(oAuth2AccessToken);
        } catch (Exception e) {
            throw new UnauthorizedException("非法的刷新token");
        }
    }

    @Override
    public void deleteUserAccessToken(Long userId) {
        List<String> clientIds = authClientService.getAllClientId();
        if (clientIds == null) {
            return;
        }
        List<String> keys = new ArrayList<>();
        for (String clientId : clientIds) {
            String key = String.format("%s_token_%s", userId.toString(), clientId);
            keys.add(key);
        }
        redisTemplate.delete(keys);
    }

    /**
     * 转换为tokenDTO
     *
     * @param accessToken
     * @return
     */
    private AccessTokenDTO toTokenDTO(OAuth2AccessToken accessToken) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setTokenType(accessToken.getTokenType());
        accessTokenDTO.setAccessToken(accessToken.getValue());
        accessTokenDTO.setExpiresIn(accessToken.getExpiresIn());
        accessTokenDTO.setRefreshToken(accessToken.getRefreshToken().getValue());
        accessTokenDTO.setScope(OAuth2Utils.formatParameterList(accessToken.getScope()));
        return accessTokenDTO;
    }

    private void sameAccountLoginLimit(OAuth2AccessToken accessToken) {
        if (enableSameAccountLoginLimit) {
            Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
            sameAccountLoginLimit.storeUserToken(
                    additionalInformation.get("context_id").toString(),
                    additionalInformation.get("user_id").toString(),
                    additionalInformation.get("client_id").toString(),
                    additionalInformation.get("jti").toString());
        }
    }

    private boolean validateRefreshToken(Map<String, Object> tokenParameters) {
        if (enableSameAccountLoginLimit) {
            return sameAccountLoginLimit.validateRefreshToken(
                    tokenParameters.get("context_id").toString(),
                    tokenParameters.get("user_id").toString(),
                    tokenParameters.get("client_id").toString(),
                    tokenParameters.get("ati").toString());
        }
        return true;
    }

    private void storeToken(String memberId, String clientId, OAuth2AccessToken accessToken) {
        String key = String.format("%s_token_%s", memberId, clientId);
        redisTemplate.opsForValue().set(key, accessToken.getValue(), 7 * 24 * 60 * 60, TimeUnit.SECONDS);
    }

}