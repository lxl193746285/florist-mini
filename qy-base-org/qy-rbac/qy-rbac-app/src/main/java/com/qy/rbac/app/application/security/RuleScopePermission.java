package com.qy.rbac.app.application.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 规则范围权限
 *
 * @author legendjw
 */
public class RuleScopePermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "rbac/rule-scope/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "rbac/rule-scope/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "rbac/rule-scope/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "rbac/rule-scope/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "rbac/rule-scope/delete");
}