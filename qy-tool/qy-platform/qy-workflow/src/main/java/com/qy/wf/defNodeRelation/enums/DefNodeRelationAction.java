package com.qy.wf.defNodeRelation.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_节点关系动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefNodeRelationAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-node-relations/index"),
    VIEW("view", "查看", "wf/def-node-relations/view"),
    CREATE("create", "创建", "wf/def-node-relations/create"),
    EDIT("update", "编辑", "wf/def-node-relations/update"),
    DELETE("delete", "删除", "wf/def-node-relations/delete");

    private DefNodeRelationAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
