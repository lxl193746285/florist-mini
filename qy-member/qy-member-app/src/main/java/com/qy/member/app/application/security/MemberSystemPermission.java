package com.qy.member.app.application.security;

import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.session.Identity;
import com.qy.uims.security.action.PermissionAction;
import com.qy.uims.security.permission.AbstractMultiOrganizationPermissionRule;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationPermissionRuleUtils;
import com.qy.uims.security.permission.OrganizationPermissionScope;

import java.util.List;

/**
 * 会员系统权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class MemberSystemPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "member/member-system/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "member/member-system/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "member/member-system/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "member/member-system/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "member/member-system/delete");
    public static PermissionAction MEMBER_LIST = new PermissionAction("member-list", "会员", "member/member/index");
    public static PermissionAction ROLE_LIST = new PermissionAction("role-list", "权限组", "organization/role/index");

    private MemberSystemDataRepository memberSystemDataRepository;

    public MemberSystemPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, MemberSystemDataRepository memberSystemDataRepository) {
        super(organizationPermissionRuleUtils);
        this.memberSystemDataRepository = memberSystemDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        MemberSystemDO entity = memberSystemDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationScope(identity, employees, permissionScopes, entity);
    }
}