package com.qy.uims.security.permission;

import com.qy.security.interfaces.GetOrganization;
import com.qy.security.interfaces.GetOrganizationCreator;
import com.qy.security.interfaces.GetOrganizationDepartmentCreator;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.rule.OrganizationPermissionRule;
import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.session.EmployeeIdentity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 抽象单公司权限规则
 *
 * @author legendjw
 */
public abstract class AbstractOrganizationPermissionRule<T, U> implements OrganizationPermissionRule<T, U> {
    private OrganizationPermissionRuleUtils organizationPermissionRuleUtils;

    public AbstractOrganizationPermissionRule(OrganizationPermissionRuleUtils organizationPermissionRuleUtils) {
        this.organizationPermissionRuleUtils = organizationPermissionRuleUtils;
    }

    /**
     * 获取指定权限范围过滤的查询对象
     *
     * @param permissionScope
     * @return
     */
    protected abstract T getFilterQuery(EmployeeIdentity identity, OrganizationPermissionScope permissionScope);

    /**
     * 判断指定的实体id数据是否在指定权限范围内
     *
     * @param id
     * @param permissionScope
     * @return
     */
    protected abstract boolean inScope(EmployeeIdentity identity, OrganizationPermissionScope permissionScope, U id);

    @Override
    public T getFilterQuery(EmployeeIdentity identity, String permission) {
        return getFilterQuery(identity, organizationPermissionRuleUtils.getOrganizationPermissionScope(identity, permission));
    }

    @Override
    public T getFilterQuery(EmployeeIdentity identity, GetPermission permission) {
        return getFilterQuery(identity, organizationPermissionRuleUtils.getOrganizationPermissionScope(identity, permission.getPermission()));
    }

    @Override
    public boolean hasPermission(EmployeeIdentity identity, String permission, U id) {
        return inScope(identity, organizationPermissionRuleUtils.getOrganizationPermissionScope(identity, permission), id);
    }

    @Override
    public boolean hasPermission(EmployeeIdentity identity, GetPermission permission, U id) {
        return inScope(identity, organizationPermissionRuleUtils.getOrganizationPermissionScope(identity, permission.getPermission()), id);
    }

    /**
     * 获取组织过滤查询
     *
     * @param identity
     * @param permissionScope
     * @return
     */
    protected OrganizationFilterQuery getOrganizationFilterQuery(EmployeeIdentity identity, OrganizationPermissionScope permissionScope) {
        if (permissionScope == null) { return null; }

        //组装组织隔离条件
        OrganizationFilterQuery filterQuery = new OrganizationFilterQuery();
        filterQuery.setOrganizationId(identity.getOrganizationId());

        return filterQuery;
    }

    /**
     * 获取组织、部门以及创建人过滤查询
     *
     * @param identity
     * @param permissionScope
     * @return
     */
    protected OrganizationFilterQuery getOrganizationAndDepartmentAndCreatorFilterQuery(EmployeeIdentity identity, OrganizationPermissionScope permissionScope) {
        if (permissionScope == null) { return null; }

        //组装组织隔离条件
        OrganizationFilterQuery filterQuery = new OrganizationFilterQuery();
        filterQuery.setOrganizationId(identity.getOrganizationId());

        //处理当前模块支持的权限规则范围
        PermissionScope scope = permissionScope.getPermissionScope();
        if (scope == null) {
            return filterQuery;
        }
        //本部门的范围
        else if (scope instanceof DepartmentPermissionScope) {
            filterQuery.setDepartmentId(identity.getDepartmentId());
        }
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            filterQuery.setEmployeeId(identity.getId());
        }

        return filterQuery;
    }

    /**
     * 获取组织、创建人过滤查询
     *
     * @param identity
     * @param permissionScope
     * @return
     */
    protected OrganizationFilterQuery getOrganizationAndCreatorFilterQuery(EmployeeIdentity identity, OrganizationPermissionScope permissionScope) {
        if (permissionScope == null) { return null; }

        //组装组织隔离条件
        OrganizationFilterQuery filterQuery = new OrganizationFilterQuery();
        filterQuery.setOrganizationId(identity.getOrganizationId());

        //处理当前模块支持的权限规则范围
        PermissionScope scope = permissionScope.getPermissionScope();
        if (scope == null) {
            return filterQuery;
        }
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            filterQuery.setEmployeeId(identity.getId());
        }

        return filterQuery;
    }

    /**
     * 判断指定实体是否在组织、部门以及创建人范围内
     *
     * @param identity
     * @param permissionScope
     * @param entity
     * @return
     */
    protected boolean inOrganizationAndDepartmentAndCreatorScope(EmployeeIdentity identity, OrganizationPermissionScope permissionScope, GetOrganizationDepartmentCreator entity) {
        //当前实体使用的组织权限范围
        if (permissionScope == null) { return false; }

        //处理当前模块支持的权限规则范围
        PermissionScope scope = permissionScope.getPermissionScope();
        if (scope == null) {
            return false;
        }
        //全部范围
        else if (scope instanceof AllPermissionScope) {
            return true;
        }
        //本部门的范围
        else if (scope instanceof DepartmentPermissionScope) {
            return entity.getDepartmentId().equals(identity.getDepartmentId());
        }
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            return entity.getCreatorId().equals(identity.getId());
        }

        return false;
    }

    /**
     * 判断指定实体是否在组织、创建人范围内
     *
     * @param identity
     * @param permissionScope
     * @param entity
     * @return
     */
    protected boolean inOrganizationAndCreatorScope(EmployeeIdentity identity, OrganizationPermissionScope permissionScope, GetOrganizationCreator entity) {
        //当前实体使用的组织权限范围
        if (permissionScope == null) { return false; }

        //处理当前模块支持的权限规则范围
        PermissionScope scope = permissionScope.getPermissionScope();
        if (scope == null) {
            return false;
        }
        //全部范围
        else if (scope instanceof AllPermissionScope) {
            return true;
        }
        //自己的范围
        else if (scope instanceof SelfPermissionScope) {
            return entity.getCreatorId().equals(identity.getId());
        }

        return false;
    }

    /**
     * 判断指定实体是否在组织范围内
     *
     * @param identity
     * @param permissionScope
     * @param entity
     * @return
     */
    protected boolean inOrganizationScope(EmployeeIdentity identity, OrganizationPermissionScope permissionScope, GetOrganization entity) {
        if (permissionScope == null) {
            return false;
        }
        return permissionScope.getOrganizationId().equals(entity.getOrganizationId());
    }
}