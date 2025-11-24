package com.qy.security.permission.rule;

/**
 * 单组织权限规则
 *
 * @author legendjw
 */
public interface OrganizationPermissionRule<T, U> extends EmployeeFilterQuery<T>, EmployeeHasPermission<U> {
}