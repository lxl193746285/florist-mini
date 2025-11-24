package com.qy.security.permission.scope;

/**
 * 权限范围
 *
 * @author legendjw
 */
public interface PermissionScope<T> {
    /**
     * 获取范围id
     *
     * @return
     */
    String getId();

    /**
     * 获取范围名称
     *
     * @return
     */
    String getName();

    /**
     * 获取范围数据
     *
     * @param data
     * @return
     */
    default T getScopeData(Object data) {
        return null;
    }
}