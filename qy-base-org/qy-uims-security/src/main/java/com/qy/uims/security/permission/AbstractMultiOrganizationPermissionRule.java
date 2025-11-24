package com.qy.uims.security.permission;

import com.qy.organization.api.dto.DepartmentBasicDTO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.security.interfaces.GetOrganization;
import com.qy.security.interfaces.GetOrganizationCreator;
import com.qy.security.interfaces.GetOrganizationDepartmentCreator;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.rule.MultiOrganizationPermissionRule;
import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.session.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 抽象多公司权限范围
 *
 * @author legendjw
 */
public abstract class AbstractMultiOrganizationPermissionRule<T, U> implements MultiOrganizationPermissionRule<T, U> {
    private OrganizationPermissionRuleUtils organizationPermissionRuleUtils;

    public AbstractMultiOrganizationPermissionRule() {
    }

    public AbstractMultiOrganizationPermissionRule(OrganizationPermissionRuleUtils organizationPermissionRuleUtils) {
        this.organizationPermissionRuleUtils = organizationPermissionRuleUtils;
    }

    /**
     * 获取指定权限范围过滤的查询对象
     *
     * @param permissionScopes
     * @return
     */
    protected abstract T getFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes);

    /**
     * 判断指定的实体id数据是否在指定权限范围内
     *
     * @param id
     * @param permissionScopes
     * @return
     */
    protected abstract boolean inScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, U id);

    @Override
    public T getFilterQuery(Identity identity, String permission) {
        List<EmployeeBasicDTO> employees = organizationPermissionRuleUtils.getUserJoinOrganizationBasicEmployees(identity);
        return getFilterQuery(identity, employees, organizationPermissionRuleUtils
                .getOrganizationPermissionScope(identity, permission));
    }

    @Override
    public T getFilterQuery(Identity identity, GetPermission permission) {
        return getFilterQuery(identity, permission.getPermission());
    }

    @Override
    public boolean hasPermission(Identity identity, String permission, U id) {
        List<EmployeeBasicDTO> employees = organizationPermissionRuleUtils.getUserJoinOrganizationBasicEmployees(identity);
        return inScope(identity, employees, organizationPermissionRuleUtils.getOrganizationPermissionScope(identity, permission), id);
    }

    @Override
    public boolean hasPermission(Identity identity, GetPermission permission, U id) {
        return hasPermission(identity, permission.getPermission(), id);
    }

    /**
     * 获取组织隔离查询
     *
     * @return
     */
    protected List<Long> getOrganizationIsolationQuery(Identity identity) {
        List<EmployeeBasicDTO> employees = organizationPermissionRuleUtils.getUserJoinOrganizationBasicEmployees(identity);
        return employees.stream().map(EmployeeBasicDTO::getOrganizationId).collect(Collectors.toList());
    }

    /**
     * 获取组织、部门以及创建人过滤查询
     *
     * @param identity
     * @param employees
     * @param permissionScopes
     * @return
     */
    protected MultiOrganizationFilterQuery getOrganizationAndDepartmentAndCreatorFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return null; }

        //组装组织隔离条件
        MultiOrganizationFilterQuery filterQuery = new MultiOrganizationFilterQuery();
        filterQuery.setOrganizationIds(this.getOrganizationIsolationQuery(identity));

        //组装权限过滤条件
        List<OrganizationFilterQuery> organizationFilterQueries = new ArrayList<>();
        for (OrganizationPermissionScope permissionScope : permissionScopes) {
            OrganizationFilterQuery organizationFilterQuery = new OrganizationFilterQuery();
            organizationFilterQuery.setOrganizationId(permissionScope.getOrganizationId());
            //当前组织的员工身份
            EmployeeBasicDTO employee = employees.stream().filter(o -> o.getOrganizationId().equals(permissionScope.getOrganizationId())).findFirst().orElse(null);

            //处理当前模块支持的权限规则范围
            PermissionScope scope = permissionScope.getPermissionScope();
            //指定部门范围
            if (scope instanceof DepartmentPermissionScope) {
                organizationFilterQuery.setDepartmentId(employee.getDepartmentId());
            }
            //自己的范围
            else if (scope instanceof SelfPermissionScope) {
                organizationFilterQuery.setEmployeeId(employee.getId());
            }

            organizationFilterQueries.add(organizationFilterQuery);
        }
        filterQuery.setPermissionFilterQueries(organizationFilterQueries);

        return filterQuery;
    }

    /**
     * 获取组织、创建人过滤查询
     *
     * @param identity
     * @param employees
     * @param permissionScopes
     * @return
     */
    protected MultiOrganizationFilterQuery getOrganizationAndCreatorFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return null; }

        //组装组织隔离条件
        MultiOrganizationFilterQuery filterQuery = new MultiOrganizationFilterQuery();
        filterQuery.setOrganizationIds(this.getOrganizationIsolationQuery(identity));

        //组装权限过滤条件
        List<OrganizationFilterQuery> organizationFilterQueries = new ArrayList<>();
        for (OrganizationPermissionScope permissionScope : permissionScopes) {
            OrganizationFilterQuery organizationFilterQuery = new OrganizationFilterQuery();
            organizationFilterQuery.setOrganizationId(permissionScope.getOrganizationId());
            //当前组织的员工身份
            EmployeeBasicDTO employee = employees.stream().filter(o -> o.getOrganizationId().equals(permissionScope.getOrganizationId())).findFirst().orElse(null);

            //处理当前模块支持的权限规则范围
            PermissionScope scope = permissionScope.getPermissionScope();
            //自己的范围
            if (scope instanceof SelfPermissionScope) {
                organizationFilterQuery.setEmployeeId(employee.getId());
            }

            organizationFilterQueries.add(organizationFilterQuery);
        }
        filterQuery.setPermissionFilterQueries(organizationFilterQueries);

        return filterQuery;
    }

    protected MultiOrganizationFilterQuery getOrganizationFilterQuery(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes) {
        if (permissionScopes == null || permissionScopes.isEmpty()) { return null; }

        //组装组织隔离条件
        MultiOrganizationFilterQuery filterQuery = new MultiOrganizationFilterQuery();
        filterQuery.setOrganizationIds(permissionScopes.stream().map(OrganizationPermissionScope::getOrganizationId).collect(Collectors.toList()));

        return filterQuery;
    }

    /**
     * 判断指定实体是否在组织、部门以及创建人范围内
     *
     * @param identity
     * @param employees
     * @param permissionScopes
     * @param entity
     * @return
     */
    protected boolean inOrganizationAndDepartmentAndCreatorScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, GetOrganizationDepartmentCreator entity) {
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
            return entity.getDepartmentId().equals(employee.getDepartmentId());
        }
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            return entity.getCreatorId().equals(employee.getId());
        }

        return false;
    }

    /**
     * 判断指定实体是否在组织、创建人范围内
     *
     * @param identity
     * @param employees
     * @param permissionScopes
     * @param entity
     * @return
     */
    protected boolean inOrganizationAndCreatorScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, GetOrganizationCreator entity) {
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
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            return entity.getCreatorId().equals(employee.getId());
        }

        return false;
    }

    /**
     * 判断指定实体是否在组织范围内
     *
     * @param identity
     * @param employees
     * @param permissionScopes
     * @param entity
     * @return
     */
    protected boolean inOrganizationScope(Identity identity, List<EmployeeBasicDTO> employees, List<OrganizationPermissionScope> permissionScopes, GetOrganization entity) {
        return permissionScopes.stream().anyMatch(p -> p.getOrganizationId().equals(entity.getOrganizationId()));
    }
}