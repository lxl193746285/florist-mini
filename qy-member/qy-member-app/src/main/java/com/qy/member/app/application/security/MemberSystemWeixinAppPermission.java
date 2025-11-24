package com.qy.member.app.application.security;

import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
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
 * 会员系统微信应用权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class MemberSystemWeixinAppPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "member/member-system-weixin-app/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "member/member-system-weixin-app/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "member/member-system-weixin-app/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "member/member-system-weixin-app/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "member/member-system-weixin-app/delete");

    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;

    public MemberSystemWeixinAppPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository) {
        super(organizationPermissionRuleUtils);
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        MemberSystemWeixinAppDO entity = memberSystemWeixinAppDataRepository.findById(id);
        if (entity == null) { return false; }

        return inOrganizationScope(identity, employees, permissionScopes, entity);
    }
}