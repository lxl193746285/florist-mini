package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.dto.ContextAndRuleDTO;
import com.qy.rbac.app.application.dto.ContextDTO;
import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;

import java.util.List;

/**
 * 授权查询服务
 *
 * @author legendjw
 */
public interface AuthQueryService {
    /**
     * 获取指定角色的权限
     *
     * @param roleId
     * @return
     */
    List<PermissionWithRuleDTO> getRolePermissions(String roleId);

    /**
     * 获取指定用户的权限
     *
     * @param userId
     * @return
     */
    List<PermissionWithRuleDTO> getUserPermissions(String userId);

    /**
     * 获取指定用户在指定上下文的权限
     *
     * @param userId
     * @param context
     * @param contextId
     * @return
     */
    List<PermissionWithRuleDTO> getUserPermissions(String userId, String context, String contextId);

    /**
     * 获取指定用户在指定上下文的指定功能模块的权限
     *
     * @param userId
     * @param context
     * @param contextId
     * @return
     */
    List<PermissionWithRuleDTO> getUserFunctionPermissions(String userId, String context, String contextId, List<String> functions);

    /**
     * 获取指定用户有指定权限的上下文
     *
     * @param userId
     * @param context
     * @param permission
     * @param systemId
     * @return
     */
    List<ContextDTO> getHasPermissionContexts(String userId, String context, String permission, Long systemId);

    /**
     * 获取指定用户有指定权限的上下文以及规则数据
     *
     * @param userId
     * @param context
     * @param permission
     * @return
     */
    List<ContextAndRuleDTO> getHasPermissionContextsWithRule(String userId, String context, String permission);

    /**
     * 获取指定用户指定的上下文的规则数据
     *
     * @param userId
     * @param context
     * @param contextId
     * @param permission
     * @param systemId
     * @return
     */
    ContextAndRuleDTO getHasPermissionContextWithRule(String userId, String context, String contextId, String permission, Long systemId);

    /**
     * 获取拥有指定权限的用户
     *
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    List<String> getHasPermissionUsers(String context, String contextId, String permission);

    /**
     * 指定用户是否有指定的权限
     *
     * @param userId
     * @param permission
     * @return
     */
    boolean isUserHasPermission(String userId, String permission);

    /**
     * 指定用户在指定上下文是否有指定的权限
     *
     * @param userId
     * @param context
     * @param contextId
     * @param permission
     * @param systemId
     * @return
     */
    boolean isUserHasPermission(String userId, String context, String contextId, String permission, Long systemId);
}