package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.rbac.app.domain.auth.Context;
import com.qy.rbac.app.infrastructure.persistence.AuthAssignmentDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthAssignmentDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthAssignmentMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色用户领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class AuthAssignmentDomainRepositoryImpl implements AuthAssignmentDomainRepository {
    private AuthAssignmentMapper authAssignmentMapper;
    private RoleManageClient roleManageClient;

    public AuthAssignmentDomainRepositoryImpl(AuthAssignmentMapper authAssignmentMapper, RoleManageClient roleManageClient) {
        this.authAssignmentMapper = authAssignmentMapper;
        this.roleManageClient = roleManageClient;
    }

    @Override
    public List<String> findUserAssignRoles(String userId, Context context) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthAssignmentDO::getUserId, userId)
                .eq(AuthAssignmentDO::getContext, context.getContext())
                .eq(AuthAssignmentDO::getContextId, context.getContextId());
        List<AuthAssignmentDO> authAssignmentDOS = authAssignmentMapper.selectList(queryWrapper);
        return authAssignmentDOS.stream().map(AuthAssignmentDO::getItemName).collect(Collectors.toList());
    }

    @Override
    public Set<Context> findUserAssignContext(String userId) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthAssignmentDO::getUserId, userId);
        List<AuthAssignmentDO> authAssignmentDOS = authAssignmentMapper.selectList(queryWrapper);
        Set<Context> contexts = new HashSet<>();
        for (AuthAssignmentDO authAssignmentDO : authAssignmentDOS) {
            contexts.add(new Context(authAssignmentDO.getContext(), authAssignmentDO.getContextId()));
        }
        return contexts;
    }

    @Override
    public List<AuthAssignmentDO> findUserAssignByRole(String roleId) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthAssignmentDO::getItemName, roleId);
        return authAssignmentMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createAuthAssignment(String userId, Context context, List<String> roleIds, Long systemId) {
        removeAuthAssignment(userId, context, systemId);
        LocalDateTime now = LocalDateTime.now();
        for (String roleId : roleIds) {
            AuthAssignmentDO authAssignmentDO = new AuthAssignmentDO();
            authAssignmentDO.setUserId(userId);
            authAssignmentDO.setContext(context.getContext());
            authAssignmentDO.setContextId(context.getContextId());
            authAssignmentDO.setItemName(roleId);
            authAssignmentDO.setCreateTime(now);
            RoleBasicDTO roleBasicDTO = roleManageClient.getByAuthItem(roleId);
            if (roleBasicDTO != null) {
                authAssignmentDO.setSystemId(Long.parseLong(roleBasicDTO.getContextId()));
            }
            if (systemId != null && authAssignmentDO.getSystemId() == null) {
                authAssignmentDO.setSystemId(systemId);
            }
            authAssignmentMapper.insert(authAssignmentDO);
        }
    }

    @Override
    @Transactional
    public void removeAuthAssignment(String userId, Context context, Long systemId) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthAssignmentDO::getUserId, userId)
                .eq(AuthAssignmentDO::getContext, context.getContext())
                .eq(AuthAssignmentDO::getContextId, context.getContextId())
                .eq(systemId != null, AuthAssignmentDO::getSystemId, systemId);
        authAssignmentMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    public void removeAuthAssignment(String userId) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthAssignmentDO::getUserId, userId);
        authAssignmentMapper.delete(queryWrapper);
    }
}
