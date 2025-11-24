package com.qy.wf.defNode.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
public class DefNodeDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 节点编码id
     */
    @ApiModelProperty(value = "节点编码id")
    private String nodeCode;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 节点名称 显示在设计图上
     */
    @ApiModelProperty(value = "节点名称 显示在设计图上")
    private String name;

    /**
     * 1人为决策，2自动处理(直接执行execute_function)，3等待外部响应(例如外部WS触发),4分支，5汇总 6结束结点（此结点执行时候自动终止进程）
     */
    @ApiModelProperty(value = "1人为决策，2自动处理(直接执行execute_function)，3等待外部响应(例如外部WS触发),4分支，5汇总 6结束结点（此结点执行时候自动终止进程）")
    private Integer nodeType;

    /**
     * 1指定人员 2部门主管 3直属主管 4角色 5发起人自选 6发起人自己 7表单内的联系人
     */
    @ApiModelProperty(value = "1指定人员 2部门主管 3直属主管 4角色 5发起人自选 6发起人自己 7表单内的联系人")
    private Integer selectUserRule;

    /**
     * 1直属领导2上上级领导3上上上级领导
     */
    @ApiModelProperty(value = "1直属领导2上上级领导3上上上级领导")
    private Integer leaderLevel;

    /**
     * 找不到主管时，由上级主管代审批
     */
    @ApiModelProperty(value = "找不到主管时，由上级主管代审批")
    private Integer leaderAuto;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 1自选一人2自选多人
     */
    @ApiModelProperty(value = "1自选一人2自选多人")
    private Integer selectPeopleNum;

    /**
     * 1全公司2指定人员（取人员表）3角色
     */
    @ApiModelProperty(value = "1全公司2指定人员（取人员表）3角色")
    private Integer selectRange;

    /**
     * 只能一个角色
     */
    @ApiModelProperty(value = "只能一个角色")
    private Long selectRole;

    @ApiModelProperty(value = "办理人表达式")
    private String selectUserRuleExp;

    /**
     * 1会签（须所有办理人处理），2或签（一名办理人处理即可）
     */
    @ApiModelProperty(value = "1会签（须所有办理人处理），2或签（一名办理人处理即可）")
    private Integer judgeType;

    /**
     * 1自动通过2自动转交管理员找不到主管时，由上级主管代审批3指定审批人员（一个人）
     */
    @ApiModelProperty(value = "1自动通过2自动转交管理员找不到主管时，由上级主管代审批3指定审批人员（一个人）")
    private Integer emptyOp;

    /**
     * 设置抄送人0不设置,1设置
     */
    @ApiModelProperty(value = "设置抄送人0不设置,1设置")
    private Integer isSetCclist;

    /**
     * 单位分钟
     */
    @ApiModelProperty(value = "单位分钟")
    private Integer timeOut;

    /**
     * 0不允许，1允许
     */
    @ApiModelProperty(value = "0不允许，1允许")
    private Integer timeOutModify;

    /**
     * 0否，1是
     */
    @ApiModelProperty(value = "0否，1是")
    private Integer timeOutAttend;

    /**
     * 所有前置节点结束才能开始
     */
    @ApiModelProperty(value = "所有前置节点结束才能开始")
    private Integer isSignLook;

    /**
     * 0总是可见，1本步骤办理人之间不可见，2针对其他步骤不可见
     */
    @ApiModelProperty(value = "0总是可见，1本步骤办理人之间不可见，2针对其他步骤不可见")
    private Integer signLook;

    /**
     * 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanAgree;

    @ApiModelProperty(value = "0无1有")
    private Integer isCanDeal;

    /**
     * 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanRefuse;

    /**
     * 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanReturn;

    /**
     * 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanInvalid;

    /**
     * 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanWithdraw;

    /**
     * 0无1有
     */
    @ApiModelProperty(value = "撤销权限0无1有")
    private Integer isCanRevoke;

    @ApiModelProperty(value = "打回权限,0无1有")
    private Integer isRepulse;

    /**
     * 下一节点选人方式 0手动，1自动
     */
    @ApiModelProperty(value = "下一节点选人方式 0手动，1自动")
    private Integer isAutoNextUser;

    /**
     * 1新建，2编辑，3删除，4下载，5打印
     */
    @ApiModelProperty(value = "1新建，2编辑，3删除，4下载，5打印")
    private Integer attachPriv;

    /**
     * Y坐标
     */
    @ApiModelProperty(value = "Y坐标")
    private Integer xPos;

    /**
     * X坐标
     */
    @ApiModelProperty(value = "X坐标")
    private Integer yPos;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;
    @ApiModelProperty(value = "办理别名,如果为空默认值为办理")
    private String dealAlias;
    @ApiModelProperty(value = "同意别名,如果为空默认值为同意")
    private String agreeAlias;
    @ApiModelProperty(value = "撤销别名,如果为空默认值为撤销")
    private String revokeAlias;
    @ApiModelProperty(value = "拒绝别名,如果为空默认值为拒绝")
    private String refuseAlias;
    @ApiModelProperty(value = "退回别名,如果为空默认值为退回")
    private String returnAlias;
    @ApiModelProperty(value = "作废别名,如果为空默认值为作废")
    private String invalidAlias;
    @ApiModelProperty(value = "撤回别名,如果为空默认值为撤回")
    private String withdrawAlias;
    @ApiModelProperty(value = "打回别名,如果为空默认值为打回")
    private String repulseAlias;
}
