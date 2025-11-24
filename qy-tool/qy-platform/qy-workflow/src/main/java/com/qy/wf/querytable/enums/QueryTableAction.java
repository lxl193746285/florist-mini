package com.qy.wf.querytable.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_查询表单动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QueryTableAction implements IArkEnumAction {
    LIST("index", "列表", "wfquery-tables/index"),
    VIEW("view", "查看", "wfquery-tables/view"),
    CREATE("create", "创建", "wfquery-tables/create"),
    EDIT("update", "编辑", "wfquery-tables/update"),
    DELETE("delete", "删除", "wfquery-tables/delete");

    private QueryTableAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
