package com.qy.uims.security.permission;

import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.permission.scope.SecurityPermissionScope;

/**
 * 通用的自己的权限范围
 */
@SecurityPermissionScope
public class SelfPermissionScope implements PermissionScope<Object> {
    @Override
    public String getId() {
        return "selfScope";
    }

    @Override
    public String getName() {
        return "通用自己的范围";
    }
}
