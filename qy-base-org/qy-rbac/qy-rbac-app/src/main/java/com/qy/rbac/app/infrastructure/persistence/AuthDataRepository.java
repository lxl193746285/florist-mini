package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthAssignmentDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemChildDO;

import java.util.List;

/**
 * 授权数据资源库
 *
 * @author legendjw
 */
public interface AuthDataRepository {
    /**
     * 查找用户下的所有角色
     *
     * @param userId
     * @return
     */
    List<String> findRolesByUser(String userId);

    /**
     * 查找指定用户指定上下文的所有角色
     *
     * @param userId
     * @param context
     * @param contextId
     * @return
     */
    List<AuthAssignmentDO> findRolesByUser(String userId, String context, String contextId);

    /**
     * 查找指定用户的权限
     *
     * @param userId
     * @return
     */
    List<AuthItemChildDO> findPermissionsByUser(String userId);

    /**
     * 查找指定用户指定上下文的权限
     *
     * @param userId
     * @return
     */
    List<AuthItemChildDO> findPermissionsByUser(String userId, String context, String contextId);

    /**
     * 查找指定角色的权限
     *
     * @param roleId
     * @return
     */
    List<AuthItemChildDO> findPermissionsByRole(String roleId);

    /**
     * 查找指定上下文下包含指定权限的用户
     *
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    List<String> getHasPermissionUsers(String context, String contextId, String permission);
}
