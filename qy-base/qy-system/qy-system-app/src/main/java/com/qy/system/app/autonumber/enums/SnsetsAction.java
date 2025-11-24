package com.qy.system.app.autonumber.enums;

import com.qy.system.app.comment.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 编号设置动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SnsetsAction implements IArkEnumAction {
    INDEX("index", "列表", "system/autonumber/index"),
    VIEW("view", "查看", "system/autonumber/view"),
    CREATE("create", "创建", "system/autonumber/create"),
    UPDATE("update", "编辑", "system/autonumber/update"),
    DELETE("delete", "删除", "system/autonumber/delete"),
    ENABLE("enable", "启用", "system/autonumber/enable"),
    DISABLE("disable", "禁用", "system/autonumber/disable");


    private SnsetsAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
