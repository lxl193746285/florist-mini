package com.qy.uims.security.permission;

import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.permission.scope.SecurityPermissionScope;

/**
 * 通用的全部的权限范围
 *
 * @author legendjw
 */
@SecurityPermissionScope
public class AllPermissionScope implements PermissionScope<Object> {
    @Override
    public String getId() {
        return "allScope";
    }

    @Override
    public String getName() {
        return "通用全部范围";
    }
}
