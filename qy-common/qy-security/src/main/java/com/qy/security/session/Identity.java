package com.qy.security.session;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.rule.UserHasPermission;

/**
 * 用户身份
 *
 * @author legendjw
 */
public interface Identity {
    /**
     * 获取用户ID
     *
     * @return
     */
    Long getId();

    /**
     * 获取用户姓名
     *
     * @return
     */
    String getName();

    /**
     * 当前用户是否拥有指定的权限
     *
     * @param permission
     * @return
     */
    boolean hasPermission(String permission);

    /**
     * 当前用户是否拥有指定的权限
     *
     * @param permission
     * @return
     */
    boolean hasPermission(GetPermission permission);

    /**
     * 当前用户在指定权限规则下对指定实体是否拥有指定权限
     *
     * @param userHasPermission
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(UserHasPermission userHasPermission, String permission, Object id);

    /**
     * 当前用户在指定权限规则下对指定实体是否拥有指定权限
     *
     * @param userHasPermission
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(UserHasPermission userHasPermission, GetPermission permission, Object id);
}