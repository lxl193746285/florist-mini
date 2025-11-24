package com.qy.security.permission.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 权限范围持有默认实现
 *
 * @author legendjw
 */
@Component
public class DefaultPermissionScopeScopeHolder implements PermissionScopeHolder {
    private ApplicationContext applicationContext;

    public DefaultPermissionScopeScopeHolder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public PermissionScope getPermissionScopeById(String id) {
        Map<String, Object> scopes = applicationContext.getBeansWithAnnotation(SecurityPermissionScope.class);
        for (Object value : scopes.values()) {
            if (value instanceof PermissionScope) {
                PermissionScope permissionScope = (PermissionScope<?>) value;
                if (permissionScope.getId().equals(id)) {
                    return permissionScope;
                }
            }
        }
        return null;
    }
}