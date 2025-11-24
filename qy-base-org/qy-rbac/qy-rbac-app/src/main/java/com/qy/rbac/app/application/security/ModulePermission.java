package com.qy.rbac.app.application.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 模块权限
 *
 * @author legendjw
 */
public class ModulePermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "rbac/module/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "rbac/module/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "rbac/module/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "rbac/module/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "rbac/module/delete");
}