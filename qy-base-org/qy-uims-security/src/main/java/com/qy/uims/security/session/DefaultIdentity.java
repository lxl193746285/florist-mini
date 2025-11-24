package com.qy.uims.security.session;

import com.qy.rbac.api.client.AuthClient;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.rule.UserHasPermission;
import com.qy.security.session.Identity;

/**
 * 默认的用户身份实现
 *
 * @author legendjw
 */
public class DefaultIdentity implements Identity {
    private Long id;
    private String name;
    private AuthClient authClient;

    public DefaultIdentity(Long id, String name, AuthClient authClient) {
        this.id = id;
        this.name = name;
        this.authClient = authClient;
    }

    @Override
    public Long getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasPermission(String permission) {
        return authClient.isUserHasPermission(id.toString(), permission);
    }

    @Override
    public boolean hasPermission(GetPermission permission) {
        return hasPermission(permission.getPermission());
    }

    @Override
    public boolean hasPermission(UserHasPermission userHasPermission, String permission, Object id) {
        return userHasPermission.hasPermission(this, permission, id);
    }

    @Override
    public boolean hasPermission(UserHasPermission userHasPermission, GetPermission permission, Object id) {
        return hasPermission(userHasPermission, permission.getPermission(), id);
    }
}
