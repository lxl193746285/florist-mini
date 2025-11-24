package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工作流_设计_节点
 *
 * @author iFeng
 * @since 2022-11-15
 */
@Data
public class WfDefNodeFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点编码
     */
    @NotBlank(message = "节点编码 不能为空")
    @ApiModelProperty(value = "节点编码")
    private String nodeCode;

    /**
     * 工作流ID
     */
    @NotNull(message = "工作流ID 不能为空")
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 公司
     */
    @NotNull(message = "公司 不能为空")
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 节点名称 显示在设计图上
     */
    @NotBlank(message = "节点名称 显示在设计图上 不能为空")
    @ApiModelProperty(value = "节点名称 显示在设计图上")
    private String name;

    /**
     * 1开始节点，2结束节点，3办理节点
     */
    @NotNull(message = "1开始节点，2结束节点，3办理节点 不能为空")
    @ApiModelProperty(value = "1开始节点，2结束节点，3办理节点")
    private Integer nodeType;

    /**
     * 1指定人员	            2部门主管	            3直属主管	            4角色	            5发起人自选	            6发起人自己	            7表单内的联系人
     */
    @NotNull(message = "1指定人员	            2部门主管	            3直属主管	            4角色	            5发起人自选	            6发起人自己	            7表单内的联系人 不能为空")
    @ApiModelProperty(value = "1指定人员	            2部门主管	            3直属主管	            4角色	            5发起人自选	            6发起人自己	            7表单内的联系人")
    private Integer selectUserRule;

    /**
     * 1直属领导2上上级领导3上上上级领导
     */
    @NotNull(message = "1直属领导2上上级领导3上上上级领导 不能为空")
    @ApiModelProperty(value = "1直属领导2上上级领导3上上上级领导")
    private Integer leaderLevel;

    /**
     * 找不到主管时，由上级主管代审批
     */
    @NotNull(message = "找不到主管时，由上级主管代审批 不能为空")
    @ApiModelProperty(value = "找不到主管时，由上级主管代审批")
    private Integer leaderAuto;

    /**
     * 角色id
     */
    @NotNull(message = "角色id 不能为空")
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 1自选一人2自选多人
     */
    @NotNull(message = "1自选一人2自选多人 不能为空")
    @ApiModelProperty(value = "1自选一人2自选多人")
    private Integer selectPeopleNum;

    /**
     * 1全公司2指定人员（取人员表）3角色
     */
    @NotNull(message = "1全公司2指定人员（取人员表）3角色 不能为空")
    @ApiModelProperty(value = "1全公司2指定人员（取人员表）3角色")
    private Integer selectRange;

    /**
     * 只能一个角色
     */
    @NotNull(message = "只能一个角色 不能为空")
    @ApiModelProperty(value = "只能一个角色")
    private Long selectRole;

    /**
     * 1会签（须所有办理人处理），2或签（一名办理人处理即可）
     */
    @NotNull(message = "1会签（须所有办理人处理），2或签（一名办理人处理即可） 不能为空")
    @ApiModelProperty(value = "1会签（须所有办理人处理），2或签（一名办理人处理即可）")
    private Integer judgeType;

    /**
     * 1自动通过2自动转交管理员找不到主管时，由上级主管代审批3指定审批人员（一个人）
     */
    @NotNull(message = "1自动通过2自动转交管理员找不到主管时，由上级主管代审批3指定审批人员（一个人） 不能为空")
    @ApiModelProperty(value = "1自动通过2自动转交管理员找不到主管时，由上级主管代审批3指定审批人员（一个人）")
    private Integer emptyOp;

    /**
     * 设置抄送人0不设置,1设置
     */
    @NotNull(message = "设置抄送人0不设置,1设置 不能为空")
    @ApiModelProperty(value = "设置抄送人0不设置,1设置")
    private Integer isSetCclist;

    /**
     * 单位分钟
     */
    @NotNull(message = "单位分钟 不能为空")
    @ApiModelProperty(value = "单位分钟")
    private Integer timeOut;

    /**
     * 0不允许，1允许
     */
    @NotNull(message = "0不允许，1允许 不能为空")
    @ApiModelProperty(value = "0不允许，1允许")
    private Integer timeOutModify;

    /**
     * 0否，1是
     */
    @NotNull(message = "0否，1是 不能为空")
    @ApiModelProperty(value = "0否，1是")
    private Integer timeOutAttend;

    /**
     * 所有前置节点结束才能开始
     */
    @NotNull(message = "所有前置节点结束才能开始 不能为空")
    @ApiModelProperty(value = "所有前置节点结束才能开始")
    private Integer isSignLook;

    /**
     * 0总是可见，1本步骤办理人之间不可见，2针对其他步骤不可见
     */
    @NotNull(message = "0总是可见，1本步骤办理人之间不可见，2针对其他步骤不可见 不能为空")
    @ApiModelProperty(value = "0总是可见，1本步骤办理人之间不可见，2针对其他步骤不可见")
    private Integer signLook;

    /**
     * 0无1有
     */
    @NotNull(message = "0无1有 不能为空")
    @ApiModelProperty(value = "0无1有")
    private Integer isCanAgree;

    /**
     * 0无1有
     */
    @NotNull(message = "0无1有 不能为空")
    @ApiModelProperty(value = "0无1有")
    private Integer isCanRefuse;

    /**
     * 0无1有
     */
    @NotNull(message = "0无1有 不能为空")
    @ApiModelProperty(value = "0无1有")
    private Integer isCanReturn;

    /**
     * 作废权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanInvalid;
    /**
     * 撤回权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanWithdraw;

    /**
     * 撤销权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanRevoke;

    /**
     * 1新建，2编辑，3删除，4下载，5打印
     */
    @NotNull(message = "1新建，2编辑，3删除，4下载，5打印 不能为空")
    @ApiModelProperty(value = "1新建，2编辑，3删除，4下载，5打印")
    private Integer attachPriv;

    /**
     * Y坐标
     */
    @NotNull(message = "Y坐标 不能为空")
    @ApiModelProperty(value = "Y坐标")
    private Integer xPos;

    /**
     * X坐标
     */
    @NotNull(message = "X坐标 不能为空")
    @ApiModelProperty(value = "X坐标")
    private Integer yPos;

    /**
     * 顺序
     */
    @NotNull(message = "顺序 不能为空")
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @NotBlank(message = "备注 不能为空")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @NotNull(message = "1启用0禁用 不能为空")
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
