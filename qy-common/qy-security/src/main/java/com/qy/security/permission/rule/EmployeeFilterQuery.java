package com.qy.security.permission.rule;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.session.EmployeeIdentity;

/**
 * 指定员工指定权限过滤的查询对象接口
 *
 * @author legendjw
 */
public interface EmployeeFilterQuery<T> {
    /**
     * 获取指定员工指定权限过滤的查询对象
     *
     * @param identity
     * @param permission
     * @return
     */
    T getFilterQuery(EmployeeIdentity identity, String permission);

    /**
     * 获取指定员工指定权限过滤的查询对象
     *
     * @param identity
     * @param permission
     * @return
     */
    T getFilterQuery(EmployeeIdentity identity, GetPermission permission);
}
