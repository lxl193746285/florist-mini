package com.qy.organization.app.application.security;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.infrastructure.persistence.EmployeeDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.uims.security.action.PermissionAction;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.AbstractMultiOrganizationPermissionRule;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationPermissionRuleUtils;
import com.qy.uims.security.permission.OrganizationPermissionScope;

import java.util.List;

/**
 * 员工权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class EmployeePermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "organization/employee/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "organization/employee/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "organization/employee/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "organization/employee/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "organization/employee/delete");
    public static PermissionAction ON_JOB = new PermissionAction("on-job", "在职", "organization/employee/on-job");
    public static PermissionAction LEAVE_JOB = new PermissionAction("leave-job", "离职", "organization/employee/leave-job");
    public static PermissionAction SET_IDENTITY = new PermissionAction("set-identity", "设置权限", "organization/employee/set-identity");
    public static PermissionAction BATCH_SET_IDENTITY = new PermissionAction("batch-set-identity", "批量设置权限", "organization/employee/batch-set-identity");
    public static PermissionAction CHANGE_USERNAME = new PermissionAction("change-username", "更换登录用户名", "organization/employee/change-username");
    public static PermissionAction CHANGE_USER_PHONE = new PermissionAction("change-user-phone", "更换登录手机号", "organization/employee/change-user-phone");
    public static PermissionAction RESET_PASSWORD = new PermissionAction("reset-password", "重置密码", "organization/employee/reset-password");

    private EmployeeDataRepository employeeDataRepository;

    public EmployeePermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, EmployeeDataRepository employeeDataRepository) {
        super(organizationPermissionRuleUtils);
        this.employeeDataRepository = employeeDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationAndCreatorFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        EmployeeDO entity = employeeDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationAndDepartmentAndCreatorScope(identity, employees, permissionScopes, entity);
    }
}