package com.qy.uims.security.permission;

import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.dto.ContextAndRuleDTO;
import com.qy.rest.memoize.Memoize;
import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.permission.scope.PermissionScopeHolder;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织权限规则工具类
 *
 * @author legendjw
 */
@Component
public class OrganizationPermissionRuleUtils {
    private AuthClient authClient;
    private PermissionScopeHolder permissionScopeHolder;
    private MemberClient memberClient;
    @Value("${qy.organization.ark-system-id}")
    private Long systemId;

    public OrganizationPermissionRuleUtils(AuthClient authClient, PermissionScopeHolder permissionScopeHolder, MemberClient memberClient) {
        this.authClient = authClient;
        this.permissionScopeHolder = permissionScopeHolder;
        this.memberClient = memberClient;
    }

    @Memoize
    public OrganizationPermissionScope getOrganizationPermissionScope(EmployeeIdentity identity, String permission) {
        MemberBasicDTO employeeBasicDTO = memberClient.getBasicMember(identity.getId());
        if (employeeBasicDTO == null) {
            return null;
        }
        ContextAndRuleDTO contextAndRuleDTO = authClient.getHasPermissionContextWithRule(employeeBasicDTO.getAccountId().toString(),
                OrganizationSessionContext.contextId, identity.getOrganizationId().toString(), permission, 1L);
        if (contextAndRuleDTO == null) {
            return null;
        }
        if (contextAndRuleDTO.getRuleScopeId() == null) {
            return new OrganizationPermissionScope(Long.valueOf(contextAndRuleDTO.getContextId()), null, null);
        }
        PermissionScope permissionScope = permissionScopeHolder.getPermissionScopeById(contextAndRuleDTO.getRuleScopeId());
        return new OrganizationPermissionScope(Long.valueOf(contextAndRuleDTO.getContextId()), permissionScope, contextAndRuleDTO.getRuleScopeData());
    }

    @Memoize
    public List<OrganizationPermissionScope> getOrganizationPermissionScope(Identity identity, String permission) {
        List<ContextAndRuleDTO> contextAndRuleDTOS = authClient.getHasPermissionContextsWithRule(identity.getId().toString(),
                OrganizationSessionContext.contextId, permission);
        List<OrganizationPermissionScope> organizationPermissionScopes = new ArrayList<>();
        if (contextAndRuleDTOS.isEmpty()) {
            return organizationPermissionScopes;
        }
        for (ContextAndRuleDTO contextAndRuleDTO : contextAndRuleDTOS) {
            PermissionScope permissionScope = permissionScopeHolder.getPermissionScopeById(contextAndRuleDTO.getRuleScopeId());
            OrganizationPermissionScope organizationPermissionScope =
                    new OrganizationPermissionScope(Long.valueOf(contextAndRuleDTO.getContextId()), permissionScope, contextAndRuleDTO.getRuleScopeData());
            organizationPermissionScopes.add(organizationPermissionScope);
        }
        return organizationPermissionScopes;
    }

    @Memoize
    public List<EmployeeBasicDTO> getUserJoinOrganizationBasicEmployees(Identity identity) {
        List<MemberBasicDTO> dtos =  memberClient.getMembersByAccountAndSystemId(identity.getId(), systemId);
        List<EmployeeBasicDTO> basicDTOS = new ArrayList<>();
        for (MemberBasicDTO dto : dtos) {
            EmployeeBasicDTO basicDTO = new EmployeeBasicDTO();
            basicDTO.setOrganizationId(dto.getOrganizationId());
            basicDTO.setUserId(dto.getAccountId());
            basicDTOS.add(basicDTO);
        }
        return basicDTOS;
    }
}
