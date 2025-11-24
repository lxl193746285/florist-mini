package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.rbac.app.domain.auth.*;
import com.qy.rbac.app.domain.auth.*;
import com.qy.rbac.app.infrastructure.persistence.AuthItemChildDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.AuthItemDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.converter.AuthItemConverter;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemChildDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthItemChildMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthItemMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限项领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class AuthItemDomainRepositoryImpl implements AuthItemDomainRepository {
    private AuthItemConverter authItemConverter;
    private AuthItemMapper authItemMapper;
    private AuthItemChildDomainRepository authItemChildDomainRepository;
    private ObjectMapper objectMapper;

    public AuthItemDomainRepositoryImpl(AuthItemConverter authItemConverter, AuthItemMapper authItemMapper,
                                        AuthItemChildDomainRepository authItemChildDomainRepository,
                                        ObjectMapper objectMapper) {
        this.authItemConverter = authItemConverter;
        this.authItemMapper = authItemMapper;
        this.authItemChildDomainRepository = authItemChildDomainRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Role findRoleById(RoleId roleId) {
        AuthItemDO authItemDO = findByName(roleId.getId());
        return authItemDO != null ? authItemConverter.toRole(authItemDO) : null;
    }

    @Override
    public Permission findPermissionById(PermissionId permissionId) {
        AuthItemDO authItemDO = findByName(permissionId.getId());
        return authItemDO != null ? authItemConverter.toPermission(authItemDO) : null;
    }

    @Override
    public Role findRootRole() {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemDO::getName, "root");
        AuthItemDO authItemDO = authItemMapper.selectOne(queryWrapper);;
        return authItemDO != null ? authItemConverter.toRole(authItemDO) : null;
    }

    @Override
    @Transactional
    public RoleId createRole(Role role) {
        AuthItemDO authItemDO = authItemConverter.roleToDO(role);
        authItemDO.setType(AuthItemType.ROLE.getId());
        authItemMapper.insert(authItemDO);
        return new RoleId(authItemDO.getName());
    }

    @Override
    @Transactional
    public RoleId updateRole(RoleId id, Role role) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthItemDO::getName, id.getId());
        AuthItemDO authItemDO = authItemConverter.roleToDO(role);
        authItemDO.setType(AuthItemType.ROLE.getId());
        authItemMapper.update(authItemDO, queryWrapper);
        return new RoleId(authItemDO.getName());
    }

    @Override
    @Transactional
    public void saveRolePermission(Role role) {
        //删除角色下所有权限节点
        LambdaQueryWrapper<AuthItemChildDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthItemChildDO::getParent, role.getId().getId());
        authItemChildDomainRepository.remove(queryWrapper);

        //新增权限节点
        LocalDateTime now = LocalDateTime.now();
        List<AuthItemChildDO> authItemChildDOS = new ArrayList<>();
        for (PermissionWithRule permission : role.getPermissions()) {
            AuthItemChildDO authItemChildDO = new AuthItemChildDO();
            authItemChildDO.setParent(role.getId().getId());
            authItemChildDO.setChild(permission.getPermissionId().getId());
            authItemChildDO.setRule(permission.getRule() != null ? ruleToString(permission.getRule()) : null);
            authItemChildDO.setCreateTime(now);
            authItemChildDOS.add(authItemChildDO);
        }
        authItemChildDomainRepository.saveBatch(authItemChildDOS);
    }

    @Override
    @Transactional
    public void saveRolePermission(Role role, PermissionWithRule permissionWithRule) {
        LambdaQueryWrapper<AuthItemChildDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthItemChildDO::getParent, role.getId().getId());
        queryWrapper.eq(AuthItemChildDO::getChild, permissionWithRule.getPermissionId().getId());
        authItemChildDomainRepository.remove(queryWrapper);

        LocalDateTime now = LocalDateTime.now();
        AuthItemChildDO authItemChildDO = new AuthItemChildDO();
        authItemChildDO.setParent(role.getId().getId());
        authItemChildDO.setChild(permissionWithRule.getPermissionId().getId());
        authItemChildDO.setRule(permissionWithRule.getRule() != null ? ruleToString(permissionWithRule.getRule()) : null);
        authItemChildDO.setCreateTime(now);
        authItemChildDomainRepository.save(authItemChildDO);
    }

    @Override
    @Transactional
    public PermissionId createPermission(Permission permission) {
        AuthItemDO authItemDO = authItemConverter.permissionToDO(permission);
        authItemDO.setType(AuthItemType.PERMISSION.getId());
        authItemMapper.insert(authItemDO);

        return new PermissionId(authItemDO.getName());
    }

    @Override
    @Transactional
    public PermissionId updatePermission(PermissionId id, Permission permission) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthItemDO::getName, id.getId());

        AuthItemDO authItemDO = authItemConverter.permissionToDO(permission);
        authItemDO.setType(AuthItemType.PERMISSION.getId());
        authItemMapper.update(authItemDO, queryWrapper);

        //更新子父表权限节点
        LambdaQueryWrapper<AuthItemChildDO> childQueryWrapper = new LambdaQueryWrapper<>();
        childQueryWrapper.eq(AuthItemChildDO::getChild, id.getId());
        AuthItemChildDO authItemChildDO = new AuthItemChildDO();
        authItemChildDO.setChild(permission.getId().getId());
        authItemChildDomainRepository.update(authItemChildDO, childQueryWrapper);

        return new PermissionId(authItemDO.getName());
    }

    @Override
    @Transactional
    public void removeRole(RoleId roleId) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemDO::getName, roleId.getId());
        authItemMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    public void removePermission(PermissionId permissionId) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemDO::getName, permissionId.getId());
        authItemMapper.delete(queryWrapper);

        //去除所有角色下的此权限
        LambdaQueryWrapper<AuthItemChildDO> authItemChildQueryWrapper = new LambdaQueryWrapper<>();
        authItemChildQueryWrapper.eq(AuthItemChildDO::getChild, permissionId.getId());
        authItemChildDomainRepository.remove(authItemChildQueryWrapper);
    }

    @Override
    public int countRoleId(String id, String excludeId) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemDO::getName, id)
                .eq(AuthItemDO::getType, AuthItemType.ROLE.getId());
        if (excludeId != null) {
            queryWrapper.ne(AuthItemDO::getName, excludeId);
        }
        return authItemMapper.selectCount(queryWrapper);
    }

    @Override
    public int countPermissionId(String id, String excludeId) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemDO::getName, id)
                .eq(AuthItemDO::getType, AuthItemType.PERMISSION.getId());
        if (excludeId != null) {
            queryWrapper.ne(AuthItemDO::getName, excludeId);
        }
        return authItemMapper.selectCount(queryWrapper);
    }

    private AuthItemDO findByName(String name) {
        LambdaQueryWrapper<AuthItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemDO::getName, name);
        return authItemMapper.selectOne(queryWrapper);
    }

    private String ruleToString(Rule rule) {
        try {
            return objectMapper.writeValueAsString(rule);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
