package com.qy.system.app.dismodel.enums;

import com.qy.system.app.comment.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 二维码配置模板动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QrcodeModelAction implements IArkEnumAction {
    LIST("index", "列表", "system/qrcode-model/index"),
    VIEW("view", "查看", "system/qrcode-model/view"),
 CREATE("create", "新增", "system/qrcode-model/create"),
    EDIT("update", "编辑", "system/qrcode-model/update"),
    DELETE("delete", "删除", "system/qrcode-model/delete");

    private QrcodeModelAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
