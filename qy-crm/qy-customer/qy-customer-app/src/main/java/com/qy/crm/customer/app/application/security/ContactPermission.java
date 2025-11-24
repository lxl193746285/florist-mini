package com.qy.crm.customer.app.application.security;

import com.qy.crm.customer.app.infrastructure.persistence.ContactDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactDO;
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
 * 联系人权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class ContactPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "oa/customer/customer/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "oa/customer/customer/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "oa/customer/customer/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "oa/customer/customer/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "oa/customer/customer/delete");

    private ContactDataRepository contactDataRepository;

    public ContactPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, ContactDataRepository contactDataRepository) {
        super(organizationPermissionRuleUtils);
        this.contactDataRepository = contactDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationAndCreatorFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        ContactDO entity = contactDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationAndCreatorScope(identity, employees, permissionScopes, entity);
    }
}
