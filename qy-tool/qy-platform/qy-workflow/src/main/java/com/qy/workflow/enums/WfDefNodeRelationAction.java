package com.qy.workflow.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点关系动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfDefNodeRelationAction  implements IArkEnumAction {
    LIST("index", "列表", "platform/workflow/wf-def-node-relations/index"),
    VIEW("view", "查看", "platform/workflow/wf-def-node-relations/view"),
    CREATE("create", "创建", "platform/workflow/wf-def-node-relations/create"),
    EDIT("update", "编辑", "platform/workflow/wf-def-node-relations/update"),
    DELETE("delete", "删除", "platform/workflow/wf-def-node-relations/delete");

    private WfDefNodeRelationAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
