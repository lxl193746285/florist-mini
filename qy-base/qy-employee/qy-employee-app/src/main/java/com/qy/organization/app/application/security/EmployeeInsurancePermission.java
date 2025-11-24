package com.qy.organization.app.application.security;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.infrastructure.persistence.EmployeeDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.session.Identity;
import com.qy.uims.security.action.PermissionAction;
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
public class EmployeeInsurancePermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("index", "列表", "hr/staff-management-user-infos/index");
    public static PermissionAction UPPER_INSURANCE = new PermissionAction("upper-insurance", "上意外险", "hr/staff-management-user-infos/upper-insurance");
    public static PermissionAction UPPER_SOCIAL_SECURITY = new PermissionAction("upper-social-security", "上社保", "hr/staff-management-user-infos/upper-social-security");
    public static PermissionAction INSURANCE_LIST = new PermissionAction("insurance-list", "意外险记录", "hr/staff-management-user-infos/insurance-list");

    private EmployeeDataRepository employeeDataRepository;

    public EmployeeInsurancePermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, EmployeeDataRepository employeeDataRepository) {
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