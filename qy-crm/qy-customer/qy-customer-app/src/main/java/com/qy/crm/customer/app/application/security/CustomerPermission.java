package com.qy.crm.customer.app.application.security;

import com.qy.crm.customer.app.infrastructure.persistence.CustomerDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.uims.security.action.PermissionAction;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.AbstractMultiOrganizationPermissionRule;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationPermissionRuleUtils;
import com.qy.uims.security.permission.OrganizationPermissionScope;

import java.util.List;

/**
 * 客户权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class CustomerPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "oa/customer/customer/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "oa/customer/customer/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "oa/customer/customer/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "oa/customer/customer/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "oa/customer/customer/delete");
    public static PermissionAction SUBMIT_AUDIT = new PermissionAction("submit_audit", "提交审核", "oa/customer/customer/submit-audit");
    public static PermissionAction AUDIT = new PermissionAction("audit", "审核", "oa/customer/customer/audit");
    public static PermissionAction AUDIT_LOG = new PermissionAction("audit_log", "审核日志", "oa/customer/customer/audit-log");
    public static PermissionAction OPEN_ACCOUNT = new PermissionAction("open_account", "开户", "oa/customer/customer/open-account");
    public static PermissionAction MODIFY_ADMIN = new PermissionAction("modify_admin", "修改超管", "oa/customer/customer/modify-admin");
    public static PermissionAction OPEN_MEMBER = new PermissionAction("open_member", "开通会员", "oa/customer/customer/open-member");
    public static PermissionAction MODIFY_MEMBER = new PermissionAction("modify_member", "修改会员", "oa/customer/customer/modify-member");

    private CustomerDataRepository customerDataRepository;

    public CustomerPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, CustomerDataRepository customerDataRepository) {
        super(organizationPermissionRuleUtils);
        this.customerDataRepository = customerDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationAndDepartmentAndCreatorFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        CustomerDO entity = customerDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationAndDepartmentAndCreatorScope(identity, employees, permissionScopes, entity);
    }
}
