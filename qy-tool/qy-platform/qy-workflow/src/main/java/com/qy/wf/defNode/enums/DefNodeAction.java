package com.qy.wf.defNode.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefNodeAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-nodes/index"),
    VIEW("view", "查看", "wf/def-nodes/view"),
    CREATE("create", "创建", "wf/def-nodes/create"),
    EDIT("update", "编辑", "wf/def-nodes/update"),
    DELETE("delete", "删除", "wf/def-nodes/delete");

    private DefNodeAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
