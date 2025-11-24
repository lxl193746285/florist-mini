package com.qy.security.permission.rule;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.session.Identity;

/**
 * 指定用户指定权限过滤的查询对象接口
 *
 * @author legendjw
 */
public interface UserFilterQuery<T> {
    /**
     * 获取指定用户指定权限过滤的查询对象
     *
     * @param identity
     * @param permission
     * @return
     */
    T getFilterQuery(Identity identity, String permission);

    /**
     * 获取指定用户指定权限过滤的查询对象
     *
     * @param identity
     * @param permission
     * @return
     */
    T getFilterQuery(Identity identity, GetPermission permission);
}