package com.qy.wf.runWf.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RunWfV2Action implements IArkEnumAction {
    LIST("index", "列表", "wf/run-wfs/detailed/index"),
    VIEW("view", "查看", "wf/run-wfs/detailed/view"),
    TRANSACT("transact", "审批", "wf/run-wfs/detailed/transact"),
    WITHDRAW("withdraw", "撤回", "wf/run-wfs/detailed/withdraw"),
    SQUASHER("squasher","撤销","wf/run-wfs/detailed/squasher"),
    VOIDPF("voidpf", "作废", "wf/run-wfs/detailed/voidpf"),
    CREATE("create", "创建", "wf/run-wfs/detailed/create"),
    EDIT("update", "编辑", "wf/run-wfs/detailed/update"),
    RECELL("recell", "撤销", "wf/run-wfs/detailed/recell"),
    DEAL("deal", "办理", "wf/run-wfs/detailed/deal"),
    AGREE("agree", "同意", "wf/run-wfs/detailed/agree"),
    REFUSE("refuse", "拒绝", "wf/run-wfs/detailed/refuse"),
    RETURN("return", "退回", "wf/run-wfs/detailed/return"),
    RESTART("restart", "重新发起", "wf/run-wfs/detailed/restart"),
    IS_REPULSE("is_repulse", "打回", "wf/run-wfs/detailed/is_repulse"),
    DELETE("delete", "删除", "wf/run-wfs/detailed/delete");

    private RunWfV2Action(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
