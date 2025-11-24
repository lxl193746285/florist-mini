package com.qy.codetable.app.application.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 代码表权限
 *
 * @author legendjw
 */
public class CodeTablePermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "system/code-table/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "system/code-table/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "system/code-table/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "system/code-table/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "system/code-table/delete");
}
