package com.qy.member.security.session;

import com.qy.member.api.client.AccountClient;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.AccountBasicDTO;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.UnauthorizedException;
import com.qy.security.session.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 会员系统会话上下文实现
 *
 * @author legendjw
 */
@Component("memberSystemSessionContext")
public class DefaultMemberSystemSessionContext implements MemberSystemSessionContext {
    private AuthClient authClient;
    private MemberClient memberClient;
    private AccountClient accountClient;
    private MenuClient menuClient;

    public DefaultMemberSystemSessionContext(AuthClient authClient, MemberClient memberClient, AccountClient accountClient, MenuClient menuClient) {
        this.authClient = authClient;
        this.memberClient = memberClient;
        this.accountClient = accountClient;
        this.menuClient = menuClient;
    }

    @Override
    public DefaultMemberIdentity getMember() {
        JwtAuthenticationToken jwtAuthenticationToken = getAuthentication();
        Object accountId = jwtAuthenticationToken.getTokenAttributes().get("user_id");
        Object memberId = jwtAuthenticationToken.getTokenAttributes().get("member_id");
        Object clientId = jwtAuthenticationToken.getTokenAttributes().get("client_id");
        if (accountId == null || memberId == null) {
            throw new UnauthorizedException("非法的会员账号");
        }
        AccountBasicDTO accountBasicDTO = accountClient.getBasicAccount(Long.valueOf(accountId.toString()));
        if (accountBasicDTO == null) {
            throw new UnauthorizedException("非法的会员账号");
        }
        MemberBasicDTO memberBasicDTO = memberClient.getBasicMember(Long.valueOf(memberId.toString()));

        if (memberBasicDTO == null) {
            throw new UnauthorizedException("非法的会员信息");
        }
        if (memberBasicDTO.getStatusId() == EnableDisableStatus.DISABLE.getId()) {
            throw new UnauthorizedException("会员账号禁用中");
        }
        Long storeId = null;
        if (jwtAuthenticationToken.getTokenAttributes().get("store_id") != null){
            storeId = Long.valueOf(jwtAuthenticationToken.getTokenAttributes().get("store_id").toString());
        }
        return new DefaultMemberIdentity(
                memberBasicDTO.getId(),
                memberBasicDTO.getName(),
                clientId.toString(),
                accountBasicDTO.getId(),
                accountBasicDTO.getName(),
                accountBasicDTO.getTypeId(),
                memberBasicDTO.getOrganizationId(),
                memberBasicDTO.getSystemId(),
                memberBasicDTO.getEmployeeId(),
                memberBasicDTO.getMemberTypeId(),
                storeId,
                authClient,
                menuClient
        );
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
