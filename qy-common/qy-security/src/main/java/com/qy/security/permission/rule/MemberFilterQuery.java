package com.qy.security.permission.rule;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.session.MemberIdentity;

/**
 * 指定会员指定权限过滤的查询对象接口
 *
 * @author legendjw
 */
public interface MemberFilterQuery<T> {
    /**
     * 获取指定会员指定权限过滤的查询对象
     *
     * @param identity
     * @param permission
     * @return
     */
    T getFilterQuery(MemberIdentity identity, String permission);

    /**
     * 获取指定会员指定权限过滤的查询对象
     *
     * @param identity
     * @param permission
     * @return
     */
    T getFilterQuery(MemberIdentity identity, GetPermission permission);
}