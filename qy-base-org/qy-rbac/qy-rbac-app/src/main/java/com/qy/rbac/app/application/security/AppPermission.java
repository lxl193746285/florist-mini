package com.qy.rbac.app.application.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 应用权限
 *
 * @author legendjw
 */
public class AppPermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "rbac/app/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "rbac/app/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "rbac/app/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "rbac/app/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "rbac/app/delete");
}