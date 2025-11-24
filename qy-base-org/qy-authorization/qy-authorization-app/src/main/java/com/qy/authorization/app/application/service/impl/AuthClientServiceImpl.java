package com.qy.authorization.app.application.service.impl;

import com.qy.authorization.app.application.command.CreateAuthClientCommand;
import com.qy.authorization.app.application.command.UpdateAuthClientCommand;
import com.qy.authorization.app.application.service.AuthClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 授权客户端服务
 *
 * @author legendjw
 */
@Service
public class AuthClientServiceImpl implements AuthClientService {
    public static Integer accessTokenValiditySeconds = 7 * 24 * 60 * 60;
    public static Integer refreshTokenValiditySeconds = 30 * 24 * 60 * 60;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    public PasswordEncoder passwordEncoder;
    private JdbcClientDetailsService clientDetailsService;

    public AuthClientServiceImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
    }

    @Override
    @Transactional
    public void createAuthClient(CreateAuthClientCommand command) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(command.getClientId());
        clientDetails.setClientSecret(command.getClientSecret());
        clientDetails.setScope(Arrays.asList("all"));
        Set<String> authorizedGrantTypes = new HashSet<>();
        if (command.getAuthorizedGrantTypes() == null) {
            authorizedGrantTypes = Stream.of("authorization_code", "refresh_token", "password").collect(Collectors.toSet());;
        }
        clientDetails.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        clientDetails.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        clientDetailsService.addClientDetails(clientDetails);
    }

    @Override
    @Transactional
    public void updateAuthClient(UpdateAuthClientCommand command) {
        //更新客户端id
        if (!command.getId().equals(command.getClientId())) {
            String updateClientId = "update oauth_client_details set client_id = ? where client_id = ?";
            jdbcTemplate.update(updateClientId, command.getClientId(), command.getId());
        }

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(command.getClientId());
        clientDetails.setClientSecret(command.getClientSecret());
        clientDetails.setScope(Arrays.asList("all"));
        Set<String> authorizedGrantTypes = new HashSet<>();
        if (command.getAuthorizedGrantTypes() == null) {
            authorizedGrantTypes = Stream.of("authorization_code", "refresh_token", "password").collect(Collectors.toSet());;
        }
        clientDetails.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        clientDetails.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        clientDetailsService.updateClientDetails(clientDetails);

        if (StringUtils.isNotBlank(command.getClientSecret())) {
            clientDetailsService.updateClientSecret(command.getClientId(), command.getClientSecret());
        }
    }

    @Override
    @Transactional
    public void deleteAuthClient(String clientId) {
        clientDetailsService.removeClientDetails(clientId);
    }

    @Override
    public List<String> getAllClientId() {
        List<ClientDetails> details = clientDetailsService.listClientDetails();
        if (details == null || details.size() < 1) {
            return null;
        }
        return details.stream().map(ClientDetails::getClientId).collect(Collectors.toList());
    }

}
