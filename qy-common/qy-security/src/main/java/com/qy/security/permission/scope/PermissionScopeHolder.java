package com.qy.security.permission.scope;

/**
 * 权限范围持有
 *
 * @author legendjw
 */
public interface PermissionScopeHolder {
    /**
     * 根据id获取权限范围
     *
     * @param id
     * @return
     */
    PermissionScope getPermissionScopeById(String id);
}
