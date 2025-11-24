package com.qy.wf.runWf.enums;

import com.qy.common.enums.IArkEnumAction;
import com.qy.uims.security.action.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_执行_工作流动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RunWfAction  implements IArkEnumAction {
    LIST("index", "列表", "wf/run-wfs/index"),
    VIEW("view", "查看", "wf/run-wfs/view"),
    TRANSACT("transact", "审批", "wf/run-wfs/transact"),
    WITHDRAW("withdraw", "撤回", "wf/run-wfs/withdraw"),
    SQUASHER("squasher","撤销","wf/run-wfs/squasher"),
    VOIDPF("voidpf", "作废", "wf/run-wfs/voidpf"),
    CREATE("create", "创建", "wf/run-wfs/create"),
    EDIT("update", "编辑", "wf/run-wfs/update"),
    RECELL("recell", "撤销", "wf/run-wfs/recell"),
    DEAL("deal", "办理", "wf/run-wfs/deal"),
    AGREE("agree", "同意", "wf/run-wfs/agree"),
    REFUSE("refuse", "拒绝", "wf/run-wfs/refuse"),
    RETURN("return", "退回", "wf/run-wfs/return"),
    RESTART("restart", "重新发起", "wf/run-wfs/restart"),
    IS_REPULSE("is_repulse", "打回", "wf/run-wfs/is_repulse"),
    DELETE("delete", "删除", "wf/run-wfs/delete");


    private RunWfAction(String id, String name, String permission) {
        this.permissionAction = PermissionAction.create(id, name, permission);
    }

    PermissionAction permissionAction;

    @Override
    public PermissionAction getPermissionAction() {
        return permissionAction;
    }
}
