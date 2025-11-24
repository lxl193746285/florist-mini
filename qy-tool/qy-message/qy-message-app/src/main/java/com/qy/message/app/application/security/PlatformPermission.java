package com.qy.message.app.application.security;

import com.qy.security.permission.rule.SecurityPermissionRule;
import com.qy.uims.security.action.PermissionAction;

/**
 * 消息平台权限
 *
 * @author legendjw
 */
@SecurityPermissionRule
public class PlatformPermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "message/platform/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "message/platform/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "message/platform/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "message/platform/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "message/platform/delete");
}