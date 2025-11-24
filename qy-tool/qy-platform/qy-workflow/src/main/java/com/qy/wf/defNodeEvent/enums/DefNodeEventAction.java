package com.qy.wf.defNodeEvent.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点事件动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefNodeEventAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-node-events/index"),
    VIEW("view", "查看", "wf/def-node-events/view"),
    CREATE("create", "创建", "wf/def-node-events/create"),
    EDIT("update", "编辑", "wf/def-node-events/update"),
    DELETE("delete", "删除", "wf/def-node-events/delete");

    private DefNodeEventAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
