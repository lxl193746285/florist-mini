package com.qy.organization.app.application.security;

import com.qy.organization.api.dto.DepartmentBasicDTO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.infrastructure.persistence.DepartmentDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.qy.uims.security.action.PermissionAction;
import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.*;

import java.util.List;

/**
 * 部门权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class DepartmentPermission extends AbstractMultiOrganizationPermissionRule<MultiOrganizationFilterQuery, Long> {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "organization/department/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "organization/department/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "organization/department/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "organization/department/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "organization/department/delete");

    private DepartmentDataRepository departmentDataRepository;

    public DepartmentPermission(OrganizationPermissionRuleUtils organizationPermissionRuleUtils, DepartmentDataRepository departmentDataRepository) {
        super(organizationPermissionRuleUtils);
        this.departmentDataRepository = departmentDataRepository;
    }

    @Override
    protected MultiOrganizationFilterQuery getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        return getOrganizationAndDepartmentAndCreatorFilterQuery(identity, employees, permissionScopes);
    }

    @Override
    protected boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, Long id) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return false; }
        DepartmentDO entity = departmentDataRepository.findById(id);
        if (entity == null) { return false; }

        //当前组织的员工身份
        EmployeeBasicDTO employee = employees.stream().filter(o -> o.getOrganizationId().equals(entity.getOrganizationId())).findFirst().orElse(null);

        //当前实体使用的组织权限范围
        OrganizationPermissionScope permissionScope = permissionScopes.stream().filter(ps -> ps.getOrganizationId().equals(entity.getOrganizationId())).findFirst().orElse(null);
        if (permissionScope == null) { return false; }

        //处理当前模块支持的权限规则范围
        PermissionScope scope = permissionScope.getPermissionScope();
        //全部范围
        if (scope instanceof AllPermissionScope) {
            return true;
        }
        //本部门的范围
        else if (scope instanceof DepartmentPermissionScope) {
            return entity.getId().equals(employee.getDepartmentId());
        }
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            return entity.getCreatorId().equals(employee.getId());
        }

        return false;
    }
}
