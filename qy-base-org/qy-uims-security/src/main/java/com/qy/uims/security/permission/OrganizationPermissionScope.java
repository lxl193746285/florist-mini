package com.qy.uims.security.permission;

import com.qy.security.permission.scope.PermissionScope;

/**
 * 组织权限范围
 *
 * @author legendjw
 */
public class OrganizationPermissionScope {
    private Long organizationId;
    private PermissionScope permissionScope;
    private Object permissionScopeData;

    public OrganizationPermissionScope(Long organizationId, PermissionScope permissionScope) {
        this.organizationId = organizationId;
        this.permissionScope = permissionScope;
    }

    public OrganizationPermissionScope(Long organizationId, PermissionScope permissionScope, Object permissionScopeData) {
        this.organizationId = organizationId;
        this.permissionScope = permissionScope;
        this.permissionScopeData = permissionScopeData;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public PermissionScope getPermissionScope() {
        return permissionScope;
    }

    public Object getPermissionScopeData() {
        return permissionScopeData;
    }
}
