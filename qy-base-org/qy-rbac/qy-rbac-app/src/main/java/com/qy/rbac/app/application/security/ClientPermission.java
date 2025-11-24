package com.qy.rbac.app.application.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 客户端权限
 *
 * @author legendjw
 */
public class ClientPermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "rbac/client/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "rbac/client/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "rbac/client/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "rbac/client/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "rbac/client/delete");
}