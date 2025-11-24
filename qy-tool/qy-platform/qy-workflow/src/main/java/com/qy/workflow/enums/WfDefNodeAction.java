package com.qy.workflow.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfDefNodeAction  implements IArkEnumAction {
    LIST("index", "列表", "paltform/workflow/wf-def-nodes/index"),
    VIEW("view", "查看", "paltform/workflow/wf-def-nodes/view"),
    CREATE("create", "创建", "paltform/workflow/wf-def-nodes/create"),
    EDIT("update", "编辑", "paltform/workflow/wf-def-nodes/update"),
    DELETE("delete", "删除", "paltform/workflow/wf-def-nodes/delete");

    private WfDefNodeAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
