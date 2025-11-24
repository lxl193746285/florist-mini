package com.qy.wf.nodeRepulse.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_打回节点动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefNodeRepulseAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-node-repulses/index"),
    VIEW("view", "查看", "wf/def-node-repulses/view"),
    CREATE("create", "创建", "wf/def-node-repulses/create"),
    EDIT("update", "编辑", "wf/def-node-repulses/update"),
    DELETE("delete", "删除", "wf/def-node-repulses/delete");

    private DefNodeRepulseAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
