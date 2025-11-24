package com.qy.workflow.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_执行_工作流动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfRunWfAction  implements IArkEnumAction {
    LIST("index", "列表", "platform/workflow/wf-run-wfs/index"),
    VIEW("view", "查看", "platform/workflow/wf-run-wfs/view"),
    CREATE("create", "创建", "platform/workflow/wf-run-wfs/create"),
    EDIT("update", "编辑", "platform/workflow/wf-run-wfs/update"),
    DELETE("delete", "删除", "platform/workflow/wf-run-wfs/delete");

    private WfRunWfAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
