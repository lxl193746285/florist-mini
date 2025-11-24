package com.qy.system.app.config.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qy.system.app.comment.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;

/**
 * 编号设置动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SystemConfigCategoryAction implements IArkEnumAction {
    INDEX("index", "列表", "system/system-config-categorys/index"),
    VIEW("view", "查看", "system/system-config-categorys/view"),
    CREATE("create", "创建", "system/system-config-categorys/create"),
    UPDATE("update", "编辑", "system/system-config-categorys/update"),
    DELETE("delete", "删除", "system/system-config-categorys/delete"),
    ;


    private SystemConfigCategoryAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
