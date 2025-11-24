package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.query.RoleQuery;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.organization.app.infrastructure.persistence.RoleDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.UserRoleDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.RoleMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.UserRoleMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class RoleDataRepositoryImpl implements RoleDataRepository {
    private RoleMapper roleMapper;
    private UserRoleMapper userRoleMapper;

    public RoleDataRepositoryImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Page<RoleDO> findByQuery(RoleQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RoleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(RoleDO::getSort)
                .orderByDesc(RoleDO::getCreateTime);
        if (filterQuery == null) {
            queryWrapper.eq(RoleDO::getId, 0);
        }
        else {
            queryWrapper.in(RoleDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(RoleDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getEmployeeId() != null,
                                            RoleDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(RoleDO::getName, query.getKeywords()).or().
                    like(RoleDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(RoleDO::getOrganizationId, query.getOrganizationId());
        }
        if (StringUtils.isNotBlank(query.getContext())) {
            queryWrapper.eq(RoleDO::getContext, query.getContext());
        }
        if (StringUtils.isNotBlank(query.getContextId())) {
            queryWrapper.eq(RoleDO::getContextId, query.getContextId());
        }
        if (query.getStatusId() != null) {
            queryWrapper.eq(RoleDO::getStatusId, query.getStatusId());
        }
        if (query.getUserId() != null) {
            List<RoleDO> roleDOS = findByOrganizationAndUser(query.getOrganizationId(), query.getUserId(), Long.valueOf(query.getContextId()));
            List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).collect(Collectors.toList());
            if (roleIds.isEmpty()) { roleIds.add(0L); }
            queryWrapper.in(RoleDO::getId, roleIds);
        }
        IPage<RoleDO> iPage = roleMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<RoleDO> findByIds(List<Long> ids) {
        if (ids.isEmpty()) { return new ArrayList<>(); }
        return roleMapper.selectBatchIds(ids);
    }

    @Override
    public RoleDO findById(Long id) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RoleDO::getId, id)
                .eq(RoleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public RoleDO findByAuthItem(String authItem) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RoleDO::getAuthItem, authItem)
                .eq(RoleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public RoleDO findByIdAndContextAndContextId(Long id, String context, Long contextId) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RoleDO::getId, id)
                .eq(!Strings.isNullOrEmpty(context),RoleDO::getContext,context)
                .eq(contextId!=null,RoleDO::getContextId,contextId)
                .eq(RoleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Long> findRoleUserIds(Long roleId) {
        LambdaQueryWrapper<UserRoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleDO::getRoleId, roleId);
        List<UserRoleDO> userRoleDOS = userRoleMapper.selectList(queryWrapper);
        if (userRoleDOS.isEmpty()) {
            return new ArrayList<>();
        }
        return userRoleDOS.stream().map(UserRoleDO::getUserId).collect(Collectors.toList());
    }

    @Override
    public List<UserRoleDO> findRoleUsers(Long roleId) {
        LambdaQueryWrapper<UserRoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleDO::getRoleId, roleId);
        return userRoleMapper.selectList(queryWrapper);
    }

    @Override
    public Integer findRoleUsersForShCount(Long roleId) {
        return userRoleMapper.getPersonNum(roleId);
    }

    @Override
    public RoleDO findByDefaultRole(DefaultRole defaultRole, Long systemId, Long organizationId) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RoleDO::getDefaultRole, defaultRole)
                .eq(RoleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(RoleDO::getContextId, systemId)
                .eq(RoleDO::getOrganizationId, organizationId)
                .last("limit 1");
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(RoleDO roleDO) {
        if (roleDO.getId() == null) {
            roleMapper.insert(roleDO);
        }
        else {
            roleMapper.updateById(roleDO);
        }
        return roleDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity employee) {
        RoleDO roleDO = findById(id);
        roleDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        roleDO.setDeletorId(employee.getId());
        roleDO.setDeletorName(employee.getName());
        roleDO.setDeleteTime(LocalDateTime.now());
        roleMapper.updateById(roleDO);
    }

    @Override
    public int countByOrganizationIdAndName(Long organizationId, String context, String contextId, String name, Long excludeId) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RoleDO::getOrganizationId, organizationId)
                .eq(RoleDO::getContext, context)
                .eq(RoleDO::getContextId, contextId)
                .eq(RoleDO::getName, name)
                .eq(RoleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(RoleDO::getId, excludeId);
        }
        return roleMapper.selectCount(queryWrapper);
    }

    @Override
    public List<RoleDO> findByOrganizationAndUser(Long organizationId, Long userId, Long systemId) {
        LambdaQueryWrapper<UserRoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserRoleDO::getOrganizationId, organizationId)
                .eq(UserRoleDO::getUserId, userId)
                .eq(UserRoleDO::getSystemId, systemId);
        List<UserRoleDO> userRoleDOS = userRoleMapper.selectList(queryWrapper);
        if (userRoleDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> roleIds = userRoleDOS.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList());
        return findByIds(roleIds);
    }

    @Override
    public void saveRoleUser(EmployeeBasicDTO employee, List<RoleDO> roleDOS) {
        userRoleMapper.delete(new LambdaQueryWrapper<UserRoleDO>()
                .eq(UserRoleDO::getOrganizationId, employee.getOrganizationId())
                .eq(UserRoleDO::getUserId, employee.getUserId()));
        LocalDateTime now = LocalDateTime.now();
        for (RoleDO roleDO : roleDOS) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setOrganizationId(employee.getOrganizationId());
            userRoleDO.setUserId(employee.getUserId());
            userRoleDO.setUserName(employee.getName());
            userRoleDO.setRoleId(roleDO.getId());
            userRoleDO.setCreateTime(now);
            userRoleDO.setSystemId(Long.valueOf(roleDO.getContextId()));
            userRoleMapper.insert(userRoleDO);
        }
    }

    @Override
    public void saveRoleUser(Long organizationId, Long systemId, Long userId, String userName, List<RoleDO> roleDOS) {
        userRoleMapper.delete(new LambdaQueryWrapper<UserRoleDO>()
                .eq(UserRoleDO::getOrganizationId, organizationId)
                .eq(UserRoleDO::getSystemId, systemId)
                .eq(UserRoleDO::getUserId, userId));
        LocalDateTime now = LocalDateTime.now();
        for (RoleDO roleDO : roleDOS) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setOrganizationId(organizationId);
            userRoleDO.setUserId(userId);
            userRoleDO.setUserName(userName);
            userRoleDO.setRoleId(roleDO.getId());
            userRoleDO.setCreateTime(now);
            userRoleDO.setSystemId(Long.valueOf(roleDO.getContextId()));
            userRoleMapper.insert(userRoleDO);
        }
    }

    @Override
    public void removeRoleUser(Long organizationId, Long userId) {
        userRoleMapper.delete(new LambdaQueryWrapper<UserRoleDO>()
                .eq(UserRoleDO::getOrganizationId, organizationId)
                .eq(UserRoleDO::getUserId, userId));
    }

    @Override
    public void setDefaultRole(RoleDO role, String defaultRole) {
        if (StringUtils.isNotBlank(defaultRole)) {
            //去除之前设置的默认项
            LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RoleDO::getOrganizationId, role.getOrganizationId());
            queryWrapper.eq(RoleDO::getContextId, role.getContextId());
            queryWrapper.eq(RoleDO::getContext, role.getContext());
            queryWrapper.eq(RoleDO::getDefaultRole, defaultRole);
            RoleDO updateRoleDO = new RoleDO();
            updateRoleDO.setDefaultRole("");
            roleMapper.update(updateRoleDO, queryWrapper);
        }

        //设置指定权限组为默认项
        LambdaQueryWrapper<RoleDO> defaultQueryWrapper = new LambdaQueryWrapper<>();
        defaultQueryWrapper
                .eq(RoleDO::getId, role.getId());
        RoleDO defaultUpdateRoleDO = new RoleDO();
        defaultUpdateRoleDO.setDefaultRole(defaultRole);
        roleMapper.update(defaultUpdateRoleDO, defaultQueryWrapper);
    }

//    @Override
//    public void setDefaultRoleForMemberSystem(RoleDO roleDO, String defaultRole) {
//        if (StringUtils.isNotBlank(defaultRole)) {
//            //去除之前设置的默认项
//            LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(RoleDO::getDefaultRole, defaultRole);
//            RoleDO updateRoleDO = new RoleDO();
//            updateRoleDO.setDefaultRole("");
//            roleMapper.update(updateRoleDO, queryWrapper);
//        }
//
//        //设置指定权限组为默认项
//        LambdaQueryWrapper<RoleDO> defaultQueryWrapper = new LambdaQueryWrapper<>();
//        defaultQueryWrapper
//                .eq(RoleDO::getId, roleDO.getId());
//        RoleDO defaultUpdateRoleDO = new RoleDO();
//        defaultUpdateRoleDO.setDefaultRole(defaultRole);
//        roleMapper.update(defaultUpdateRoleDO, defaultQueryWrapper);
//    }

}
