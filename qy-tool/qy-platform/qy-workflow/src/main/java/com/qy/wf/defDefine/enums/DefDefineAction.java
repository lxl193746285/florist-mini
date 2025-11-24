package com.qy.wf.defDefine.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_设计_定义
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefDefineAction implements IArkEnumAction {
    LIST("index", "列表", "wf/def-defines/index"),
    VIEW("view", "详细", "wf/def-defines/view"),
    CREATE("create", "创建", "wf/def-defines/create"),
    EDIT("update", "编辑", "wf/def-defines/update"),
    DESIGN("design","设计","wf/def-defines/design"),
    DELETE("delete", "删除", "wf/def-defines/delete");

    private DefDefineAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
