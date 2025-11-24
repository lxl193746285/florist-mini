package com.qy.uims.security.session;

import com.qy.identity.api.client.UserClient;
import com.qy.member.api.client.AccountClient;
import com.qy.member.api.dto.AccountBasicDTO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rest.exception.UnauthorizedException;
import com.qy.security.session.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 会话上下文实现
 *
 * @author legendjw
 */
@Component("sessionContext")
public class DefaultSessionContext implements SessionContext {
    private UserClient userClient;
    private AuthClient authClient;
    private AccountClient accountClient;

    public DefaultSessionContext(UserClient userClient, AuthClient authClient, AccountClient accountClient) {
        this.userClient = userClient;
        this.authClient = authClient;
        this.accountClient = accountClient;
    }

    @Override
    public Identity getUser() {
        JwtAuthenticationToken jwtAuthenticationToken = getAuthentication();
        Object userId = jwtAuthenticationToken.getTokenAttributes().get("user_id");
        if (userId == null) {
            throw new UnauthorizedException("非法的用户信息");
        }
        // UserBasicDTO userBasicDTO = userClient.getBasicUserById(Long.valueOf(userId.toString()));
        // if (userBasicDTO == null) {
        //     throw new UnauthorizedException("非法的用户信息");
        // }
        AccountBasicDTO accountBasicDTO = accountClient.getBasicAccount(Long.parseLong(userId.toString()));
        // return new DefaultIdentity(userBasicDTO.getId(), userBasicDTO.getName(), authClient);
        return new DefaultIdentity(accountBasicDTO.getId(), accountBasicDTO.getName(), authClient);
    }

    @Override
    public Client getClient() {
        JwtAuthenticationToken jwtAuthenticationToken = getAuthentication();
        Object clientId = jwtAuthenticationToken.getTokenAttributes().get("client_id");
        if (clientId == null) {
            throw new UnauthorizedException("非法的客户端");
        }
        return new DefaultClient(clientId.toString());
    }

    @Override
    public AccessToken getAccessToken() {
        JwtAuthenticationToken jwtAuthenticationToken = getAuthentication();
        String accessToken = jwtAuthenticationToken.getToken().getTokenValue();
        return new DefaultAccessToken(accessToken);
    }

    @Override
    public boolean isGuest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return true;
        }
        return false;
    }

    @Override
    public String getUserId() {
        JwtAuthenticationToken jwtAuthenticationToken = getAuthentication();
        Object userId = jwtAuthenticationToken.getTokenAttributes().get("user_id");
        if (userId == null) {
            throw new UnauthorizedException("非法的用户信息");
        }
        return userId.toString();
    }

    /**
     * 获取认证类
     *
     * @return
     */
    private JwtAuthenticationToken getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("非法的身份认证");
        }
        if ("anonymousUser".equals(authentication.getPrincipal())){
            throw new UnauthorizedException("非法的身份认证");
        }
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        //如果访问令牌是刷新令牌则报错
        if (jwtAuthenticationToken.getTokenAttributes().containsKey("ati")) {
            throw new UnauthorizedException("非法的身份认证");
        }
        return jwtAuthenticationToken;
    }
}
