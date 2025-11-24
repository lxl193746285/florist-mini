package com.qy.member.app.application.security;

import com.qy.member.app.application.repository.AccountDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
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
 * 会员账号权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class MemberAccountPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "member/account/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "member/account/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "member/account/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "member/account/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "member/account/delete");
    public static PermissionAction STATUS = new PermissionAction("status", "设置状态", "member/account/status");
    public static PermissionAction AUTHORIZE = new PermissionAction("authorize", "设置权限", "member/account/authorize");

    private AccountDataRepository accountDataRepository;

    public MemberAccountPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, AccountDataRepository accountDataRepository) {
        super(organizationPermissionRuleUtils);
        this.accountDataRepository = accountDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        MemberAccountDO entity = accountDataRepository.findById(id);
        if (entity == null) { return false; }

//        return inOrganizationScope(identity, employees, permissionScopes, entity);
        return false;
    }
}