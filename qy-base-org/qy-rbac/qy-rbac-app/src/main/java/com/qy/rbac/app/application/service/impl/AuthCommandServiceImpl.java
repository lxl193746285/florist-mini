package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.command.*;
import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import com.qy.rbac.app.application.service.AuthCommandService;
import com.qy.rbac.app.application.service.RoleCommandService;
import com.qy.rbac.app.domain.auth.*;
import com.qy.rbac.app.infrastructure.persistence.AuthAssignmentDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.AuthItemDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.MenuDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthAssignmentDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;
import com.qy.rest.exception.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 授权命令服务实现
 *
 * @author legendjw
 */
@Service
public class AuthCommandServiceImpl implements AuthCommandService {
    private AuthItemDomainRepository authItemDomainRepository;
    private AuthAssignmentDomainRepository authAssignmentDomainRepository;
    private RoleCommandService roleCommandService;
    private MenuDataRepository menuDataRepository;
    private RedisTemplate redisTemplate;

    public AuthCommandServiceImpl(AuthItemDomainRepository authItemDomainRepository,
                                  AuthAssignmentDomainRepository authAssignmentDomainRepository,
                                  RoleCommandService roleCommandService, MenuDataRepository menuDataRepository,
                                  RedisTemplate redisTemplate) {
        this.authItemDomainRepository = authItemDomainRepository;
        this.authAssignmentDomainRepository = authAssignmentDomainRepository;
        this.roleCommandService = roleCommandService;
        this.menuDataRepository = menuDataRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public void authorizeRole(AuthorizeRoleCommand command) {
        Role role = authItemDomainRepository.findRoleById(new RoleId(command.getRoleId()));
        if (role == null) {
            throw new NotFoundException("要授权的角色不存在");
        }
        List<PermissionWithRule> permissionWithRules = command.getPermissions().stream().map(p -> {
            return new PermissionWithRule(new PermissionId(p.getPermission()), p.getRuleScopeId() == null ? null : new Rule(p.getRuleScopeId(), p.getRuleScopeData()));
        }).collect(Collectors.toList());
        role.authorize(permissionWithRules);

        authItemDomainRepository.saveRolePermission(role);

        //清除权限缓存
        List<AuthAssignmentDO> authAssignmentDOS = authAssignmentDomainRepository.findUserAssignByRole(role.getId().getId());
        if (authAssignmentDOS.isEmpty()) { return; }
        Set<String> userIds = authAssignmentDOS.stream().map(AuthAssignmentDO::getUserId).collect(Collectors.toSet());
        for (String userId : userIds) {
            clearUserPermissionCache(userId);
        }
        for (AuthAssignmentDO authAssignmentDO : authAssignmentDOS) {
            clearUserContextPermissionCache(authAssignmentDO.getUserId(), authAssignmentDO.getContext(), authAssignmentDO.getContextId());
        }
    }

    @Override
    @Transactional
    public void authorizeRoleSh(AuthorizeRoleCommand command) {
        Role role = authItemDomainRepository.findRoleById(new RoleId(command.getRoleId()));
        if (role == null) {
            throw new NotFoundException("要授权的角色不存在");
        }
        //清除权限缓存
        List<AuthAssignmentDO> authAssignmentDOS = authAssignmentDomainRepository.findUserAssignByRole(role.getId().getId());
        if (authAssignmentDOS.isEmpty()) { return; }
        Set<String> userIds = authAssignmentDOS.stream().map(AuthAssignmentDO::getUserId).collect(Collectors.toSet());
        for (String userId : userIds) {
            clearUserPermissionCache(userId);
        }
        for (AuthAssignmentDO authAssignmentDO : authAssignmentDOS) {
            clearUserContextPermissionCache(authAssignmentDO.getUserId(), authAssignmentDO.getContext(), authAssignmentDO.getContextId());
        }
    }

    @Override
    @Transactional
    public void assignRoleToUser(AssignRoleToUserCommand command) {
        authAssignmentDomainRepository.createAuthAssignment(command.getUserId(), new Context(command.getContext(),
                command.getContextId()), command.getRoleIds(), command.getSystemId());

        //清除权限缓存
        clearUserPermissionCache(command.getUserId());
        clearUserContextPermissionCache(command.getUserId(), command.getContext(), command.getContextId());
    }

    @Override
    @Transactional
    public void revokeUserRole(RevokeUserRoleCommand command) {
        authAssignmentDomainRepository.removeAuthAssignment(command.getUserId(), new Context(command.getContext(), command.getContextId()), null);

        //清除权限缓存
        clearUserPermissionCache(command.getUserId());
        clearUserContextPermissionCache(command.getUserId(), command.getContext(), command.getContextId());
    }

    @Override
    @Transactional
    public void createRoleAndAuthorize(CreateRoleAndAuthorizeCommand command) {
        //创建角色
        Role role = authItemDomainRepository.findRoleById(new RoleId(command.getRoleId()));
        if (role == null) {
            RoleId roleId = roleCommandService.createRole(command.getRoleId(), command.getRoleDescription());
            role = authItemDomainRepository.findRoleById(roleId);
        }
        //给角色授权
        AuthorizeRoleCommand authorizeRoleCommand = new AuthorizeRoleCommand();
        authorizeRoleCommand.setRoleId(role.getId().getId());
        authorizeRoleCommand.setPermissions(command.getPermissions());
        authorizeRole(authorizeRoleCommand);
        //分配角色
        List<String> roleIds = new ArrayList<>();
        roleIds.add(role.getId().getId());
        AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
        assignRoleToUserCommand.setUserId(command.getUserId());
        assignRoleToUserCommand.setRoleIds(roleIds);
        assignRoleToUserCommand.setContext(command.getContext());
        assignRoleToUserCommand.setContextId(command.getContextId());
        assignRoleToUserCommand.setSystemId(command.getSystemId());
        assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    @Transactional
    public void copyUserPermission(CopyUserPermissionCommand command) {
        List<String> roleIds = authAssignmentDomainRepository.findUserAssignRoles(command.getSourceUserId(), new Context(command.getContext(), command.getContextId()));
        AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
        assignRoleToUserCommand.setUserId(command.getToUserId());
        assignRoleToUserCommand.setRoleIds(roleIds);
        assignRoleToUserCommand.setContext(command.getContext());
        assignRoleToUserCommand.setContextId(command.getContextId());
        assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    @Transactional
    public void authorizePermissionToRoot(PermissionWithRule permissionWithRule) {
        Role role = authItemDomainRepository.findRootRole();
        if (role == null) {
            return;
        }
        authItemDomainRepository.saveRolePermission(role, permissionWithRule);
    }

    @Override
    @Transactional
    public void authorizeAllPermissionToRoot() {
        Role role = authItemDomainRepository.findRootRole();
        if (role == null) {
            return;
        }
        List<MenuDO> menuDOS = menuDataRepository.findAllPermissionMenus();
        List<PermissionWithRuleDTO> permissions = new ArrayList<>();
        for (MenuDO menuDO : menuDOS) {
            List<MenuRuleDO> menuRuleDOS = menuDataRepository.findMenuRulesByMenuId(menuDO.getId());
            PermissionWithRuleDTO permission = new PermissionWithRuleDTO();
            permission.setPermission(menuDO.getAuthItem());
            if (!menuRuleDOS.isEmpty()) {
                permission.setRuleScopeId(menuRuleDOS.get(0).getScopeId());
            }
            permissions.add(permission);
        }
        //给角色授权
        AuthorizeRoleCommand authorizeRoleCommand = new AuthorizeRoleCommand();
        authorizeRoleCommand.setRoleId(role.getId().getId());
        authorizeRoleCommand.setPermissions(permissions);
        authorizeRole(authorizeRoleCommand);
    }

    /**
     * 清除以人为单位的权限缓存
     *
     * @param userId
     */
    private void clearUserPermissionCache(String userId) {

//        Cache userPermissionCache = cacheManager.getCache(CacheConfig.rbacUserPermissionCacheName);
//        userPermissionCache.evictIfPresent(userId);
        redisTemplate.delete(userId);
    }

    /**
     * 清除以人+组织为单位的权限缓存
     *
     * @param userId
     */
    private void clearUserContextPermissionCache(String userId, String context, String contextId) {
//        Cache userContextPermissionCache = cacheManager.getCache(CacheConfig.rbacUserPermissionCacheName);
        String userContextCacheKey = String.format("%s_%s_%s", userId, context, contextId);
        String userContextOrganizationCacheKey = String.format("%s_%s", userId, context);
//        userContextPermissionCache.evictIfPresent(userContextCacheKey);
//        userContextPermissionCache.evictIfPresent(userContextOrganizationCacheKey);
        redisTemplate.delete(userContextCacheKey);
        redisTemplate.delete(userContextOrganizationCacheKey);
    }
}
