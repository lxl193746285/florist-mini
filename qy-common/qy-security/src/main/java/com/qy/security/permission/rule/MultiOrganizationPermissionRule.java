package com.qy.security.permission.rule;

/**
 * 多组织权限规则
 *
 * @author legendjw
 */
public interface MultiOrganizationPermissionRule<T, U> extends UserFilterQuery<T>, UserHasPermission<U> {
}