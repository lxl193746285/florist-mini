package com.qy.wf.defMap.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_映射表 记录业务表与工作流id关联动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefMapAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-maps/index"),
    VIEW("view", "查看", "wf/def-maps/view"),
    CREATE("create", "创建", "wf/def-maps/create"),
    EDIT("update", "编辑", "wf/def-maps/update"),
    DELETE("delete", "删除", "wf/def-maps/delete");

    private DefMapAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
