package com.qy.security.permission.rule;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.session.MemberIdentity;

/**
 * 指定会员针对指定实体id是否拥有指定权限接口
 *
 * @author legendjw
 */
public interface MemberHasPermission<U> {
    /**
     * 获取指定会员针对指定实体id是否具有指定的权限
     *
     * @param identity
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(MemberIdentity identity, String permission, U id);

    /**
     * 获取指定会员针对指定实体id是否具有指定的权限
     *
     * @param identity
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(MemberIdentity identity, GetPermission permission, U id);
}