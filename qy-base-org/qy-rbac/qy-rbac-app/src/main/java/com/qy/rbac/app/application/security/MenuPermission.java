package com.qy.rbac.app.application.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 菜单权限
 *
 * @author legendjw
 */
public class MenuPermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "rbac/menu/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "rbac/menu/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "rbac/menu/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "rbac/menu/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "rbac/menu/delete");
    public static PermissionAction CREATE_CHILD = new PermissionAction("create_child", "创建子菜单", "rbac/menu/create");
}