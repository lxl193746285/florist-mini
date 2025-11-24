package com.qy.wf.defNodeForm.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_表单动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefNodeFormAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-node-forms/index"),
    VIEW("view", "查看", "wf/def-node-forms/view"),
    CREATE("create", "创建", "wf/def-node-forms/create"),
    EDIT("update", "编辑", "wf/def-node-forms/update"),
    DELETE("delete", "删除", "wf/def-node-forms/delete");

    private DefNodeFormAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
