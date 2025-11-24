package com.qy.wf.nodetable.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点表单动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NodeTableAction implements IArkEnumAction {
    LIST("index", "列表", "wfnode-tables/index"),
    VIEW("view", "查看", "wfnode-tables/view"),
    CREATE("create", "创建", "wfnode-tables/create"),
    EDIT("update", "编辑", "wfnode-tables/update"),
    DELETE("delete", "删除", "wfnode-tables/delete");

    private NodeTableAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
