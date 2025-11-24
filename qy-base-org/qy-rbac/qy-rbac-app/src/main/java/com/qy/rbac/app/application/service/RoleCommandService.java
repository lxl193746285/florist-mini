package com.qy.rbac.app.application.service;

import com.qy.rbac.app.domain.auth.RoleId;

/**
 * 角色命令服务
 *
 * @author legendjw
 */
public interface RoleCommandService {
    /**
     * 创建角色
     *
     * @param  id
     * @param description
     * @return
     */
    RoleId createRole(String id, String description);

    /**
     * 更新角色
     *
     * @param id
     * @param newId
     * @param description
     * @return
     */
    void updateRole(String id, String newId, String description);

    /**
     * 删除角色
     *
     * @param id
     */
    void deleteRole(String id);
}
