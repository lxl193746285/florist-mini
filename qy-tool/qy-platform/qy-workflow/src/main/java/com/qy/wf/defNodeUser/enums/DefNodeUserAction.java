package com.qy.wf.defNodeUser.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点人员动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefNodeUserAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-node-users/index"),
    VIEW("view", "查看", "wf/def-node-users/view"),
    CREATE("create", "创建", "wf/def-node-users/create"),
    EDIT("update", "编辑", "wf/def-node-users/update"),
    DELETE("delete", "删除", "wf/def-node-users/delete");

    private DefNodeUserAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
