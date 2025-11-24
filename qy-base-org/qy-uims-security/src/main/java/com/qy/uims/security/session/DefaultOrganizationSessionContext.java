package com.qy.uims.security.session;

import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.UnauthorizedException;
import com.qy.security.session.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 组织会话上下文实现
 *
 * @author legendjw
 */
@Component("organizationSessionContext")
public class DefaultOrganizationSessionContext implements OrganizationSessionContext {
    private SessionContext sessionContext;
    private AuthClient authClient;
    private OrganizationClient organizationClient;
    private MemberClient memberClient;
    private MenuClient menuClient;

    private static Long defaultSystemId = 1L;

    public DefaultOrganizationSessionContext(SessionContext sessionContext, AuthClient authClient, OrganizationClient organizationClient,
                                             MemberClient memberClient, MenuClient menuClient) {
        this.sessionContext = sessionContext;
        this.authClient = authClient;
        this.organizationClient = organizationClient;
        this.memberClient = memberClient;
        this.menuClient = menuClient;
    }

    @Override
    public Identity getUser() {
        return sessionContext.getUser();
    }

    @Override
    public EmployeeIdentity getEmployee() {
        Identity user = sessionContext.getUser();
        Object tokenOrganizationId = getAuthentication().getTokenAttributes().get("organization_id");
        //如果令牌中没有激活的组织则取用户的主组织
        if (tokenOrganizationId == null) {
            OrganizationBasicDTO organization = organizationClient.getUserJoinPrimaryOrganization(user.getId());
            if (organization == null) {
                throw new NotFoundException("未找到指定认证用户对应的员工身份");
            }
            return getEmployee(organization.getId());
        } else {
            return getEmployee(Long.valueOf(tokenOrganizationId.toString()));
        }
    }

    @Override
    public EmployeeIdentity getEmployee(Long organizationId) {
        // Identity user = sessionContext.getUser();
        Object tokenSystemId = getAuthentication().getTokenAttributes().get("system_id");
        if (tokenSystemId == null) {
            return getEmployee(organizationId, defaultSystemId);
        }
        return getEmployee(organizationId, Long.valueOf(tokenSystemId.toString()));
    }

    @Override
    public EmployeeIdentity getEmployee(Long organizationId, Long systemId) {
        Identity user = sessionContext.getUser();

        MemberBasicDTO memberBasicDTO = memberClient.getBasicAccountSystemMember(user.getId(), systemId, organizationId);
        if (memberBasicDTO == null) {
            throw new NotFoundException("未找到指定的会员账号");
        }
        return new DefaultEmployeeIdentity(memberBasicDTO.getId(), memberBasicDTO.getName(), memberBasicDTO.getOrganizationId(), null,
                memberBasicDTO.getSystemId(), memberBasicDTO.getAccountId(), null, authClient, menuClient);
    }

    @Override
    public Client getClient() {
        return sessionContext.getClient();
    }

    @Override
    public AccessToken getAccessToken() {
        return sessionContext.getAccessToken();
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
        if ("anonymousUser".equals(authentication.getPrincipal())) {
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
