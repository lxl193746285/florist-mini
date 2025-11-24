package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.dto.ContextAndRuleDTO;
import com.qy.rbac.app.application.dto.ContextDTO;
import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import com.qy.rbac.app.application.dto.RuleDTO;
import com.qy.rbac.app.application.service.AuthQueryService;
import com.qy.rbac.app.domain.auth.Context;
import com.qy.rbac.app.infrastructure.persistence.AuthAssignmentDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.AuthDataRepository;
import com.qy.rbac.app.infrastructure.persistence.MenuDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemChildDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 授权查询服务实现
 *
 * @author legendjw
 */
@Service
public class AuthQueryServiceImpl implements AuthQueryService {
    private AuthDataRepository authDataRepository;
    private AuthAssignmentDomainRepository authAssignmentDomainRepository;
    private MenuDataRepository menuDataRepository;
    private ObjectMapper objectMapper;
    private RedisTemplate redisTemplate;

    public AuthQueryServiceImpl(AuthDataRepository authDataRepository,
                                AuthAssignmentDomainRepository authAssignmentDomainRepository,
                                MenuDataRepository menuDataRepository, ObjectMapper objectMapper,
                                RedisTemplate redisTemplate) {
        this.authDataRepository = authDataRepository;
        this.authAssignmentDomainRepository = authAssignmentDomainRepository;
        this.menuDataRepository = menuDataRepository;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<PermissionWithRuleDTO> getRolePermissions(String roleId) {
        List<AuthItemChildDO> authItemChildDOS = authDataRepository.findPermissionsByRole(roleId);
        return authItemChildDOS.stream().map(a -> authItemChildToPermissionWithRule(a)).collect(Collectors.toList());
    }

    @Override
    public List<PermissionWithRuleDTO> getUserPermissions(String userId) {
        Object cache = redisTemplate.opsForValue().get(userId);
//        Cache cache = cacheManager.getCache(CacheConfig.rbacUserPermissionCacheName);
//        String cacheKey = userId;
//        if (cache.get(cacheKey) != null) { return cache.get(cacheKey, List.class); }
        if (cache != null) {
            return (List<PermissionWithRuleDTO>) cache;
        }

        List<AuthItemChildDO> authItemChildDOS = authDataRepository.findPermissionsByUser(userId);
        List<PermissionWithRuleDTO> permissionWithRuleDTOS = authItemChildDOS.stream().map(a -> authItemChildToPermissionWithRule(a)).collect(Collectors.toList());
        permissionWithRuleDTOS = removeDuplicates(permissionWithRuleDTOS);

//        cache.put(cacheKey, permissionWithRuleDTOS);
        redisTemplate.opsForValue().set(userId, permissionWithRuleDTOS);
        return permissionWithRuleDTOS;
    }

    @Override
    public List<PermissionWithRuleDTO> getUserPermissions(String userId, String context, String contextId) {
//        Cache cache = cacheManager.getCache(CacheConfig.rbacUserPermissionCacheName);
        String cacheKey = contextId != null ? String.format("%s_%s_%s", userId, context, contextId) : String.format("%s_%s", userId, context);
//        if (cache.get(cacheKey) != null) { return cache.get(cacheKey, List.class); }
        Object cache = redisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            return (List<PermissionWithRuleDTO>) cache;
        }

        List<AuthItemChildDO> authItemChildDOS = authDataRepository.findPermissionsByUser(userId, context, contextId);
        List<PermissionWithRuleDTO> permissionWithRuleDTOS = authItemChildDOS.stream().map(a ->
                authItemChildToPermissionWithRule(a)).collect(Collectors.toList());
        permissionWithRuleDTOS = removeDuplicates(permissionWithRuleDTOS);

//        cache.put(cacheKey, permissionWithRuleDTOS);
        redisTemplate.opsForValue().set(cacheKey, permissionWithRuleDTOS);
        return permissionWithRuleDTOS;
    }

    @Override
    public List<PermissionWithRuleDTO> getUserFunctionPermissions(String userId, String context, String contextId, List<String> functions) {
        List<MenuDO> menuDOS = menuDataRepository.findChildPermissionMenusByFunction(functions);
        if (menuDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<PermissionWithRuleDTO> permissions = getUserPermissions(userId, context, contextId);
        List<PermissionWithRuleDTO> functionPermissions = new ArrayList<>();
        for (MenuDO menuDO : menuDOS) {
            PermissionWithRuleDTO permission = permissions.stream().filter(p -> p.getPermission().equals(menuDO.getAuthItem())).findFirst().orElse(null);
            if (permission != null) {
                permission.setMenuName(StringUtils.isNotBlank(menuDO.getAliasName()) ? menuDO.getAliasName() : menuDO.getName());
                functionPermissions.add(permission);
            }
        }
        return functionPermissions;
    }

    @Override
    public List<ContextDTO> getHasPermissionContexts(String userId, String context, String permission, Long systemId) {
        Set<Context> contexts = authAssignmentDomainRepository.findUserAssignContext(userId);
        List<ContextDTO> contextDTOS = new ArrayList<>();
        for (Context c : contexts) {
            if (c.getContext().equals(context) && isUserHasPermission(userId, c.getContext(), c.getContextId(), permission, systemId)) {
                contextDTOS.add(new ContextDTO(c.getContext(), c.getContextId()));
            }
        }
        return contextDTOS;
    }

    @Override
    public List<ContextAndRuleDTO> getHasPermissionContextsWithRule(String userId, String context, String permission) {
        Set<Context> contexts = authAssignmentDomainRepository.findUserAssignContext(userId);
        List<ContextAndRuleDTO> contextDTOS = new ArrayList<>();
        for (Context c : contexts) {
            if (c.getContext().equals(context)) {
                List<PermissionWithRuleDTO> permissions = getUserPermissions(userId, c.getContext(), c.getContextId());
                PermissionWithRuleDTO permissionWithRuleDTO = permissions.stream().filter(p -> p.getPermission().equals(permission)).findFirst().orElse(null);
                if (permissionWithRuleDTO != null) {
                    contextDTOS.add(new ContextAndRuleDTO(c.getContext(), c.getContextId(), permissionWithRuleDTO.getRuleScopeId(), permissionWithRuleDTO.getRuleScopeData()));
                }
            }
        }
        return contextDTOS;
    }

    @Override
    public ContextAndRuleDTO getHasPermissionContextWithRule(String userId, String context, String contextId, String permission, Long systemId) {
        List<PermissionWithRuleDTO> permissions = getUserPermissions(userId, context, contextId);
        PermissionWithRuleDTO permissionWithRuleDTO = permissions.stream().filter(p -> p.getPermission().equals(permission)
                && systemId.compareTo(p.getSystemId()) == 0).findFirst().orElse(null);
        if (permissionWithRuleDTO == null) {
            return null;
        }
        return new ContextAndRuleDTO(context, contextId, permissionWithRuleDTO.getRuleScopeId(), permissionWithRuleDTO.getRuleScopeData());
    }

    @Override
    public List<String> getHasPermissionUsers(String context, String contextId, String permission) {
        return authDataRepository.getHasPermissionUsers(context, contextId, permission);
    }

    @Override
    public boolean isUserHasPermission(String userId, String permission) {
        List<PermissionWithRuleDTO> permissions = getUserPermissions(userId);
        return permissions.stream().anyMatch(p -> p.getPermission().equals(permission));
    }

    @Override
    public boolean isUserHasPermission(String userId, String context, String contextId, String permission, Long systemId) {
        List<PermissionWithRuleDTO> permissions = getUserPermissions(userId, context, contextId);
        return permissions.stream().anyMatch(p -> p.getPermission().equals(permission) && systemId.compareTo(p.getSystemId()) == 0);
    }

    /**
     * 转换原始的规则为规则DTO
     *
     * @param rule
     * @return
     */
    private RuleDTO toRuleDTO(String rule) {
        if (StringUtils.isBlank(rule)) {
            return null;
        }
        try {
            return objectMapper.readValue(rule, RuleDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换授权子项目为权限规则
     *
     * @param authItemChildDO
     * @return
     */
    private PermissionWithRuleDTO authItemChildToPermissionWithRule(AuthItemChildDO authItemChildDO) {
        PermissionWithRuleDTO permission = new PermissionWithRuleDTO();
        RuleDTO ruleDTO = toRuleDTO(authItemChildDO.getRule());
        permission.setPermission(authItemChildDO.getChild());
        permission.setSystemId(authItemChildDO.getSystemId());
        if (ruleDTO != null) {
            permission.setRuleScopeId(ruleDTO.getId());
            permission.setRuleScopeData(ruleDTO.getData());
        }
        return permission;
    }

    /**
     * 去除重复的授权权限
     *
     * @param permissionWithRuleDTOS
     * @return
     */
    private List<PermissionWithRuleDTO> removeDuplicates(List<PermissionWithRuleDTO> permissionWithRuleDTOS) {
        List<PermissionWithRuleDTO> data = new ArrayList<>();
        Map<String, PermissionWithRuleDTO> existPermissions = new HashMap<>();
        for (PermissionWithRuleDTO permission : permissionWithRuleDTOS) {
            if (!existPermissions.containsKey(permission.getPermission() + permission.getSystemId())) {
                existPermissions.put(permission.getPermission() + permission.getSystemId(), permission);

                data.add(permission);
            }
        }
        return data;
    }
}
