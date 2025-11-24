package com.qy.wf.var.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_变量动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VarAction implements IArkEnumAction {
    LIST("index", "列表", "wf/vars/index"),
    VIEW("view", "查看", "wf/vars/view"),
    CREATE("create", "创建", "wf/vars/create"),
    EDIT("update", "编辑", "wf/vars/update"),
    DELETE("delete", "删除", "wf/vars/delete");

    private VarAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
