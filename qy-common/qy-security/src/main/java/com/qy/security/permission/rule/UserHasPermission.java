package com.qy.security.permission.rule;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.session.Identity;

/**
 * 指定用户针对指定实体id是否拥有指定权限接口
 *
 * @author legendjw
 */
public interface UserHasPermission<U> {
    /**
     * 获取指定用户针对指定实体id是否具有指定的权限
     *
     * @param identity
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(Identity identity, String permission, U id);

    /**
     * 获取指定用户针对指定实体id是否具有指定的权限
     *
     * @param identity
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(Identity identity, GetPermission permission, U id);
}