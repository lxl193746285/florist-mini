package com.qy.organization.app.application.security;

import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.infrastructure.persistence.OrganizationDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.permission.rule.UserHasPermission;
import com.qy.security.session.Identity;
import com.qy.uims.security.action.PermissionAction;

import java.util.Optional;

/**
 * 组织权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class OrganizationPermission implements UserHasPermission<Object> {
    public static PermissionAction LIST = new PermissionAction("index", "列表", "organization/organization/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "organization/organization/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "organization/organization/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "organization/organization/update");
    public static PermissionAction OPEN_ACCOUNT = new PermissionAction("open-account", "开户", "organization/organization/open-account");
    public static PermissionAction CHANGE_ACCOUNT = new PermissionAction("chang-account", "超管变更", "organization/organization/chang-account");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "organization/organization/delete");

    private OrganizationDataRepository organizationDataRepository;
    private EmployeeClient employeeClient;

    public OrganizationPermission(OrganizationDataRepository organizationDataRepository, EmployeeClient employeeClient) {
        this.organizationDataRepository = organizationDataRepository;
        this.employeeClient = employeeClient;
    }

    @Override
    public boolean hasPermission(Identity identity, String permission, Object id) {
        OrganizationDO organizationDO = id instanceof Long ? organizationDataRepository.findById((Long) id) : (OrganizationDO) id;
        if (organizationDO == null) {
            throw new NotFoundException("未找到指定的组织");
        }
        EmployeeBasicDTO superAdmin = employeeClient.getOrganizationCreator(organizationDO.getId());
//        boolean isSuperAdmin = identity.getId().equals(superAdmin.getUserId());
        // 使用Java 8的Optional来优雅地处理可能为null的情况
        boolean isSuperAdmin = Optional.ofNullable(superAdmin)
                .map(emp -> identity.getId().equals(emp.getUserId()))
                .orElse(false);
        boolean isCreator = identity.getId().equals(organizationDO.getCreatorId());

        return isCreator || isSuperAdmin;
    }

    @Override
    public boolean hasPermission(Identity identity, GetPermission permission, Object id) {
        return hasPermission(identity, permission.getPermission(), id);
    }
}