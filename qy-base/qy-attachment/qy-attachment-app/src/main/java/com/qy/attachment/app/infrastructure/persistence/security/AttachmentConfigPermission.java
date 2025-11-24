package com.qy.attachment.app.infrastructure.persistence.security;

import com.qy.uims.security.action.PermissionAction;

/**
 * 附件设置权限
 *
 * @author legendjw
 */
public class AttachmentConfigPermission {
    public static PermissionAction LIST = new PermissionAction("list", "列表", "system/attachment-config/index");
    public static PermissionAction VIEW = new PermissionAction("view", "查看", "system/attachment-config/view");
    public static PermissionAction CREATE = new PermissionAction("create", "创建", "system/attachment-config/create");
    public static PermissionAction UPDATE = new PermissionAction("update", "编辑", "system/attachment-config/update");
    public static PermissionAction DELETE = new PermissionAction("delete", "删除", "system/attachment-config/delete");
}
