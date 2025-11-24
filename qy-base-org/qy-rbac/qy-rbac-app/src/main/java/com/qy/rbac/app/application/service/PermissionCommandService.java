package com.qy.rbac.app.application.service;

import com.qy.rbac.app.domain.auth.PermissionId;

/**
 * 权限节点命令服务
 *
 * @author legendjw
 */
public interface PermissionCommandService {
    /**
     * 创建权限节点
     *
     * @param  id
     * @param description
     * @return
     */
    PermissionId createPermission(String id, String description);

    /**
     * 更新权限节点
     *
     * @param id
     * @param newId
     * @param description
     * @return
     */
    void updatePermission(String id, String newId, String description);

    /**
     * 删除权限节点
     *
     * @param id
     */
    void deletePermission(String id);
}
