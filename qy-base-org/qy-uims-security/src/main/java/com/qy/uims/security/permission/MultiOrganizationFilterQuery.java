package com.qy.uims.security.permission;

import com.qy.security.permission.rule.PermissionFilterQuery;

import java.util.List;

/**
 * 多组织过滤查询对象
 *
 * @author legendjw
 */
public class MultiOrganizationFilterQuery implements PermissionFilterQuery {
    /**
     * 组织隔离id集合
     */
    private List<Long> organizationIds;

    /**
     * 各组织权限查询对象
     */
    private List<OrganizationFilterQuery> permissionFilterQueries;

    public List<Long> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public List<OrganizationFilterQuery> getPermissionFilterQueries() {
        return permissionFilterQueries;
    }

    public void setPermissionFilterQueries(List<OrganizationFilterQuery> permissionFilterQueries) {
        this.permissionFilterQueries = permissionFilterQueries;
    }
}