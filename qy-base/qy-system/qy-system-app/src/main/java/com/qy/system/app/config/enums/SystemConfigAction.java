package com.qy.system.app.config.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qy.system.app.comment.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;

/**
 * 编号设置动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SystemConfigAction implements IArkEnumAction {
    INDEX("index", "列表", "system/system-configs/index"),
    VIEW("view", "查看", "system/system-configs/view"),
    CREATE("create", "创建", "system/system-configs/create"),
    UPDATE("update", "编辑", "system/system-configs/update"),
    DELETE("delete", "删除", "system/system-configs/delete"),
    ;


    private SystemConfigAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
