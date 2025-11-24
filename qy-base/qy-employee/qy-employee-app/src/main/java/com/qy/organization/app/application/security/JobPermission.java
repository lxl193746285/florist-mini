package com.qy.organization.app.application.security;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.infrastructure.persistence.JobDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import com.qy.uims.security.action.PermissionAction;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.AbstractMultiOrganizationPermissionRule;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationPermissionRuleUtils;
import com.qy.uims.security.permission.OrganizationPermissionScope;

import java.util.List;

/**
 * 岗位权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class JobPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "organization/job/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "organization/job/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "organization/job/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "organization/job/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "organization/job/delete");

    private JobDataRepository jobDataRepository;

    public JobPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, JobDataRepository jobDataRepository) {
        super(organizationPermissionRuleUtils);
        this.jobDataRepository = jobDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationAndCreatorFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        JobDO entity = jobDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationAndCreatorScope(identity, employees, permissionScopes, entity);
    }
}