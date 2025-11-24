package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.query.RoleQuery;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.UserRoleDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 权限组数据资源库
 *
 * @author legendjw
 */
public interface RoleDataRepository {
    /**
     * 分页查询权限组
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<RoleDO> findByQuery(RoleQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据id集合查询权限组
     *
     * @param ids
     * @return
     */
    List<RoleDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询权限组
     *
     * @param id
     * @return
     */
    RoleDO findById(Long id);

    /**
     * 根据ID查询权限组
     *
     * @param authItem
     * @return
     */
    RoleDO findByAuthItem(String authItem);

    /**
     * 根据ID,context,contextId查询权限组
     * @param id
     * @param context
     * @param contextId
     * @return
     */
    RoleDO findByIdAndContextAndContextId(Long id, String context, Long contextId);

    /**
     * 获取指定权限组下的用户id集合
     *
     * @param roleId
     * @return
     */
    List<Long> findRoleUserIds(Long roleId);

    /**
     * 获取指定权限组下的用户
     *
     * @param roleId
     * @return
     */
    List<UserRoleDO> findRoleUsers(Long roleId);

    Integer findRoleUsersForShCount(Long roleId);


    /**
     * 根据类型获取默认权限组
     *
     * @param defaultRole
     * @param systemId
     * @param organizationId
     * @return
     */
    RoleDO findByDefaultRole(DefaultRole defaultRole, Long systemId, Long organizationId);

    /**
     * 保存一个权限组
     *
     * @param roleDO
     * @return
     */
    Long save(RoleDO roleDO);

    /**
     * 移除一个权限组
     *
     * @param id
     * @param employee
     */
    void remove(Long id, EmployeeIdentity employee);

    /**
     * 查找指定组织指定名称的数量
     *
     * @param organizationId
     * @param context
     * @param contextId
     * @param name
     * @param excludeId
     * @return
     */
    int countByOrganizationIdAndName(Long organizationId, String context, String contextId, String name, Long excludeId);

    /**
     * 获取指定用户的权限组
     *
     * @param organizationId
     * @param userId
     * @param systemId
     * @return
     */
    List<RoleDO> findByOrganizationAndUser(Long organizationId, Long userId, Long systemId);

    /**
     * 保存权限组用户
     *
     * @param employee
     * @param roleDOS
     */
    void saveRoleUser(EmployeeBasicDTO employee, List<RoleDO> roleDOS);

    /**
     * 保存权限组用户
     *
     * @param organizationId
     * @param userId
     * @param userName
     * @param roleDOS
     */
    void saveRoleUser(Long organizationId, Long systemId, Long userId, String userName, List<RoleDO> roleDOS);

    /**
     * 移除权限组用户
     *
     * @param organizationId
     * @param userId
     */
    void removeRoleUser(Long organizationId, Long userId);

    /**
     * 设置默认权限组
     *
     * @param role
     * @param defaultRole
     */
    void setDefaultRole(RoleDO role, String defaultRole);

//    /**
//     * 会员系统-设置默认权限组
//     * @param roleDO
//     * @param defaultRole
//     */
//    void setDefaultRoleForMemberSystem(RoleDO roleDO, String defaultRole);
}
