package com.qy.organization.app.application.security;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.infrastructure.persistence.RoleDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.qy.uims.security.action.PermissionAction;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.AbstractMultiOrganizationPermissionRule;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationPermissionRuleUtils;
import com.qy.uims.security.permission.OrganizationPermissionScope;

import java.util.List;

/**
 * 角色权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class RolePermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("index", "列表", "organization/role/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "organization/role/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "organization/role/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "organization/role/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "organization/role/delete");
    public static PermissionAction COPY = new PermissionAction("copy", "复制", "organization/role/copy");
    public static PermissionAction AUTHORIZE = new PermissionAction("authorize", "授权", "organization/role/authorize");
    public static PermissionAction SET_DEFAULT_ROLE = new PermissionAction("set-default-role", "设置默认权限组", "organization/role/set-default-role");
    public static PermissionAction MEMBER_SET_DEFAULT_ROLE = new PermissionAction("member-set-default-role", "设置默认权限组", "organization/role/member-set-default-role");

    private RoleDataRepository roleDataRepository;

    public RolePermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, RoleDataRepository roleDataRepository) {
        super(organizationPermissionRuleUtils);
        this.roleDataRepository = roleDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationAndCreatorFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        RoleDO entity = roleDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationAndCreatorScope(identity, employees, permissionScopes, entity);
    }
}