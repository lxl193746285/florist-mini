package com.qy.member.security.session;

import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.dto.MenuBasicDTO;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.action.Action;
import com.qy.security.permission.rule.MemberHasPermission;
import com.qy.security.session.MemberIdentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证会员默认实现
 *
 * @author legendjw
 */
public class DefaultMemberIdentity implements MemberIdentity {
    private Long id;
    private String name;
    private String clientId;
    private Long accountId;
    private String accountName;
    private Integer accountType;
    private Long organizationId;
    private Long memberSystemId;
    private Long employeeId;
    private Integer memberType;
    private Long storeId;
    private AuthClient authClient;
    private MenuClient menuClient;

    public DefaultMemberIdentity(Long id, String name,String clientId, Long accountId, String accountName, Integer accountType, Long organizationId,
                                 Long memberSystemId, Long employeeId, Integer memberType, Long storeId, AuthClient authClient, MenuClient menuClient) {
        this.id = id;
        this.name = name;
        this.clientId = clientId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.organizationId = organizationId;
        this.memberSystemId = memberSystemId;
        this.employeeId = employeeId;
        this.memberType = memberType;
        this.storeId = storeId;
        this.authClient = authClient;
        this.menuClient = menuClient;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Integer getAccountType() {
        return accountType;
    }

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public Long getEmployeeId() {
        return employeeId;
    }

    @Override
    public Long getMemberSystemId() {
        return memberSystemId;
    }

    @Override
    public Integer getMemberType() {
        return memberType;
    }

    @Override
    public Long getStoreId() {
        return storeId;
    }

    @Override
    public boolean hasPermission(String permission) {
        return authClient.isUserHasPermission(accountId.toString(), "organization", organizationId.toString(), permission,
                memberSystemId);
    }

    @Override
    public boolean hasPermission(GetPermission permission) {
        return hasPermission(permission.getPermission());
    }

    @Override
    public boolean hasPermission(MemberHasPermission memberHasPermission, String permission, Object id) {
        return memberHasPermission.hasPermission(this, permission, id);
    }

    @Override
    public boolean hasPermission(MemberHasPermission memberHasPermission, GetPermission permission, Object id) {
        return hasPermission(memberHasPermission, permission.getPermission(), id);
    }

    @Override
    public List<Action> getActions(String code) {
        List<MenuBasicDTO> menuBasicDTOS = menuClient.getAuthByCode(code, 1);
        List<Action> actions = new ArrayList<>();
        if (menuBasicDTOS == null) {
            return actions;
        }
        menuBasicDTOS.stream().map(menuBasicDTO -> {
            if (hasPermission(menuBasicDTO.getAuthItem())) {
                List<String> ids = Arrays.asList(menuBasicDTO.getAuthItem().split("/"));
                Action action = new Action(ids.get(ids.size() - 1), menuBasicDTO.getName());
                actions.add(action);
            }
            return menuBasicDTO;
        }).collect(Collectors.toList());
        return actions;
    }

    @Override
    public boolean hasPermission(String code, String permissionAction) {
        List<MenuBasicDTO> menuBasicDTOS = menuClient.getAllAuthByCode(code);
        if (menuBasicDTOS == null) {
            return false;
        }
        for (MenuBasicDTO dto : menuBasicDTOS) {
            if (permissionAction.equals(dto.getAuthItem().substring(dto.getAuthItem().lastIndexOf("/") + 1))) {
                return hasPermission(dto.getAuthItem());
            }
        }
        return false;
    }
}
