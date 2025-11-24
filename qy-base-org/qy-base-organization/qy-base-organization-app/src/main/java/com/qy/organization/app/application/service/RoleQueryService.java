package com.qy.organization.app.application.service;

import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.query.RoleQuery;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 权限组查询服务
 *
 * @author legendjw
 */
public interface RoleQueryService {
    /**
     * 查询权限组
     *
     * @param query
     * @param identity
     * @return
     */
    Page<RoleDTO> getRoles(RoleQuery query, Identity identity);

    /**
     * 根据id集合获取基本的权限组信息
     *
     * @param ids
     * @return
     */
    List<RoleBasicDTO> getBasicRoles(List<Long> ids);

    /**
     * 根据ID查询权限组
     *
     * @param id
     * @return
     */
    RoleDTO getRoleById(Long id);

    /**
     * 根据ID查询权限组
     *
     * @param authItem
     * @return
     */
    RoleBasicDTO getRoleByAuthItem(String authItem);

    /**
     * 根据ID查询权限组
     *
     * @param id
     * @return
     */
    RoleDTO getRoleById(Long id, Identity identity);

    /**
     * 获取指定的默认权限组
     *
     * @param defaultRole
     * @param systemId
     * @param organizationId
     * @return
     */
    RoleBasicDTO getDefaultRole(DefaultRole defaultRole, Long systemId, Long organizationId);

    /**
     * 获取指定权限组的权限
     *
     * @param roleId
     * @return
     */
    List<PermissionWithRuleDTO> getRolePermissions(Long roleId);

    /**
     * 获取指定权限组的菜单权限勾选信息
     *
     * @param roleId
     * @param identity
     * @return
     */
    RoleMenuPermissionDTO getRoleMenuPermission(Long roleId, Identity identity);

    /**
     * 获取指定用户的权限组
     *
     * @param organizationId
     * @param userId
     * @param systemId
     * @return
     */
    List<RoleBasicDTO> getRolesByOrganizationAndUser(Long organizationId, Long userId, Long systemId);

    /**
     * 获取指定权限组下的用户
     *
     * @return
     */
    List<RoleUserDTO> getRoleUsers(Long roleId);

    Page<AuthRoleUserDTO> getAuthUsers(Long id, AuthRoleUserQueryDTO query);
}