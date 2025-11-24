package com.qy.member.app.application.security;

import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
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
 * 会员权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class MemberPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "member/member/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "member/member/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "member/member/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "member/member/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "member/member/delete");
    public static PermissionAction STATUS = new PermissionAction("status", "设置状态", "member/member/status");
    public static PermissionAction AUTHORIZE = new PermissionAction("authorize", "设置权限", "member/member/authorize");
    public static PermissionAction STORE_AUDIT = new PermissionAction("store-audit", "商城审核", "member/member/store-audit");
    public static PermissionAction PLATFORM_AUDIT = new PermissionAction("platform-audit", "平台审核", "member/member/platform-audit");
    public static PermissionAction AUDIT_LOG = new PermissionAction("audit-log", "审核日志", "member/member/audit-log");
    public static PermissionAction LEVEL = new PermissionAction("level", "设置等级", "member/member/level");

    private MemberDataRepository memberDataRepository;

    public MemberPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, MemberDataRepository memberDataRepository) {
        super(organizationPermissionRuleUtils);
        this.memberDataRepository = memberDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        MemberDO entity = memberDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationScope(identity, employees, permissionScopes, entity);
    }
}