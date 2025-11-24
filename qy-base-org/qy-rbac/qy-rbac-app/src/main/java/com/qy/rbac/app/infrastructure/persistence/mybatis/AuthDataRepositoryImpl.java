package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.rbac.app.infrastructure.persistence.AuthDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthAssignmentDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemChildDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthAssignmentMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthItemChildMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class AuthDataRepositoryImpl implements AuthDataRepository {
    private AuthAssignmentMapper authAssignmentMapper;
    private AuthItemChildMapper authItemChildMapper;

    public AuthDataRepositoryImpl(AuthAssignmentMapper authAssignmentMapper, AuthItemChildMapper authItemChildMapper) {
        this.authAssignmentMapper = authAssignmentMapper;
        this.authItemChildMapper = authItemChildMapper;
    }

    @Override
    public List<String> findRolesByUser(String userId) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(AuthAssignmentDO::getItemName)
                .eq(AuthAssignmentDO::getUserId, userId);
        List<AuthAssignmentDO> authAssignmentDOS = authAssignmentMapper.selectList(queryWrapper);
        return authAssignmentDOS.stream().map(AuthAssignmentDO::getItemName).collect(Collectors.toList());
    }

    @Override
    public List<AuthAssignmentDO> findRolesByUser(String userId, String context, String contextId) {
        LambdaQueryWrapper<AuthAssignmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthAssignmentDO::getUserId, userId)
                .eq(AuthAssignmentDO::getContext, context);
        if (contextId != null) {
            queryWrapper.eq(AuthAssignmentDO::getContextId, contextId);
        }
        List<AuthAssignmentDO> authAssignmentDOS = authAssignmentMapper.selectList(queryWrapper);
        return authAssignmentDOS;
    }

    @Override
    public List<AuthItemChildDO> findPermissionsByUser(String userId) {
        List<String> roleIds = findRolesByUser(userId);
        if (roleIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AuthItemChildDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(AuthItemChildDO::getParent, roleIds);
        return authItemChildMapper.selectList(queryWrapper);
    }

    @Override
    public List<AuthItemChildDO> findPermissionsByUser(String userId, String context, String contextId) {
        List<AuthAssignmentDO> roles = findRolesByUser(userId, context, contextId);
        if (roles.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AuthItemChildDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(AuthItemChildDO::getParent, roles.stream().map(AuthAssignmentDO::getItemName).collect(Collectors.toList()));
        List<AuthItemChildDO> authItemChildDOS = authItemChildMapper.selectList(queryWrapper);
        authItemChildDOS.stream().map(authItemChildDO -> {
            for (AuthAssignmentDO authAssignmentDO : roles){
                if (authAssignmentDO.getItemName().equals(authItemChildDO.getParent())){
                    authItemChildDO.setSystemId(authAssignmentDO.getSystemId());
                }
            }
            return authItemChildDO;
        }).collect(Collectors.toList());
        return authItemChildDOS;
    }

    @Override
    public List<AuthItemChildDO> findPermissionsByRole(String roleId) {
        LambdaQueryWrapper<AuthItemChildDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthItemChildDO::getParent, roleId);
        return authItemChildMapper.selectList(queryWrapper);
    }

    @Override
    public List<String> getHasPermissionUsers(String context, String contextId, String permission) {
        LambdaQueryWrapper<AuthItemChildDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(AuthItemChildDO::getParent)
                .eq(AuthItemChildDO::getChild, permission);
        List<AuthItemChildDO> authItemChildDOS = authItemChildMapper.selectList(queryWrapper);
        List<String> parents = authItemChildDOS.stream().map(AuthItemChildDO::getParent).collect(Collectors.toList());
        if (parents.isEmpty()) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<AuthAssignmentDO> assignmentQueryWrapper = new LambdaQueryWrapper<>();
        assignmentQueryWrapper
                .select(AuthAssignmentDO::getUserId)
                .in(AuthAssignmentDO::getItemName, parents)
                .eq(AuthAssignmentDO::getContext, context)
                .eq(AuthAssignmentDO::getContextId, contextId);
        return authAssignmentMapper.selectObjs(assignmentQueryWrapper).stream().map(o -> o.toString()).collect(Collectors.toList());
    }
}
