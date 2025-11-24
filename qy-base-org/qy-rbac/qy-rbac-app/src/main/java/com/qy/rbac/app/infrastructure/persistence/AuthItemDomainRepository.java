package com.qy.rbac.app.infrastructure.persistence;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.rbac.app.domain.auth.*;
import com.qy.rbac.app.domain.auth.*;
import com.qy.rbac.app.infrastructure.persistence.mybatis.AuthItemDomainRepositoryImpl;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemDO;

/**
 * 权限项领域资源库
 *
 * @author legendjw
 */
public interface AuthItemDomainRepository{
    /**
     * 根据角色id获取角色
     *
     * @param roleId
     * @return
     */
    Role findRoleById(RoleId roleId);

    /**
     * 根据权限id获取权限
     *
     * @param permissionId
     * @return
     */
    Permission findPermissionById(PermissionId permissionId);

    /**
     * 获取默认超管角色
     *
     * @return
     */
    Role findRootRole();

    /**
     * 保存一个角色
     *
     * @param role
     * @return
     */
    RoleId createRole(Role role);

    /**
     * 保存一个角色
     *
     * @param id
     * @param role
     * @return
     */
    RoleId updateRole(RoleId id, Role role);

    /**
     * 保存角色权限
     *
     * @param role
     */
    void saveRolePermission(Role role);

    /**
     * 保存角色的指定一个权限
     *
     * @param role
     * @param permissionWithRule
     */
    void saveRolePermission(Role role, PermissionWithRule permissionWithRule);

    /**
     * 创建一个权限节点
     *
     * @param permission
     * @return
     */
    PermissionId createPermission(Permission permission);

    /**
     * 更新一个权限节点
     *
     * @param id
     * @param permission
     * @return
     */
    PermissionId updatePermission(PermissionId id, Permission permission);

    /**
     * 移除一个角色
     *
     * @param roleId
     */
    void removeRole(RoleId roleId);

    /**
     * 移除一个权限
     *
     * @param permissionId
     */
    void removePermission(PermissionId permissionId);

    /**
     * 获取指定角色id的数量
     *
     * @param id
     * @param excludeId
     * @return
     */
    int countRoleId(String id, String excludeId);

    /**
     * 获取指定角色id的数量
     *
     * @param id
     * @param excludeId
     * @return
     */
    int countPermissionId(String id, String excludeId);
}
