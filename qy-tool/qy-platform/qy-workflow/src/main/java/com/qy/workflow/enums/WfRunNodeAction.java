package com.qy.workflow.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_执行_节点动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfRunNodeAction  implements IArkEnumAction {
    LIST("index", "列表", "platform/workflow/wf-run-nodes/index"),
    VIEW("view", "查看", "platform/workflow/wf-run-nodes/view"),
    CREATE("create", "创建", "platform/workflow/wf-run-nodes/create"),
    EDIT("update", "编辑", "platform/workflow/wf-run-nodes/update"),
    DELETE("delete", "删除", "platform/workflow/wf-run-nodes/delete");

    private WfRunNodeAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
