package com.qy.common.enums;

/**
 * 按钮操作权限
 */
public interface IArkEnumAction extends IArkPermissionAction {
    default String getPermission() {
        return getPermissionAction().getPermission();
    }
}