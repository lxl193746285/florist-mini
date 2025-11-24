package com.qy.wf.runNode.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_执行_节点动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RunNodeAction implements IArkEnumAction {
    LIST("index", "列表", "wf/run-nodes/index"),
    VIEW("view", "查看", "wf/run-nodes/view"),
    CREATE("create", "创建", "wf/run-nodes/create"),
    EDIT("update", "编辑", "wf/run-nodes/update"),
    DELETE("delete", "删除", "wf/run-nodes/delete");

    private RunNodeAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
