package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.domain.auth.Context;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthAssignmentDO;

import java.util.List;
import java.util.Set;

/**
 * 角色用户领域资源库
 *
 * @author legendjw
 */
public interface AuthAssignmentDomainRepository {
    /**
     * 获取用户分配的所有角色
     *
     * @param userId
     * @param context
     * @return
     */
    List<String> findUserAssignRoles(String userId, Context context);

    /**
     * 获取用户分配的所有上下文
     *
     * @param userId
     * @return
     */
    Set<Context> findUserAssignContext(String userId);

    /**
     * 获取指定角色授权的用户以及上下文
     *
     * @param roleId
     * @return
     */
    List<AuthAssignmentDO> findUserAssignByRole(String roleId);

    /**
     * 创建角色用户关系
     *
     * @param userId
     * @param context
     * @param roleIds
     * @param systemId
     */
    void createAuthAssignment(String userId, Context context, List<String> roleIds, Long systemId);

    /**
     * 移除指定上下文指定用户的角色
     *
     * @param userId
     * @param context
     * @param systemId
     */
    void removeAuthAssignment(String userId, Context context, Long systemId);

    /**
     * 移除指定用户所有角色
     *
     * @param userId
     */
    void removeAuthAssignment(String userId);
}
