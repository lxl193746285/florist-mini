package com.qy.system.app.comment.enums;

/**
 * 按钮操作权限
 */
public interface IArkEnumAction extends IArkPermissionAction {
    default String getPermission() {
        return getPermissionAction().getPermission();
    }
}