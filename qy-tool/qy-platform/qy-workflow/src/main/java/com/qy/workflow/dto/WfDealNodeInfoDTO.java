package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流_办理页面显示参数
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfDealNodeInfoDTO   implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 下一节点选人方式
     */
    private  Integer isAutoNextUser;

    /**
     * 节点类型1开始，2办理，3结束
     */
    @ApiModelProperty(value = "结束节点类型1开始，2办理，3结束")
    private Integer type;

    /**
     * 下一节点继续办理，只有最后一个节点的上一个节点没有。
     *
     */
    private Integer canContinue;

    /**
     * 办理权限
     */
    private  Integer canDeal;

    /**
     * 同意权限
     */
    private Integer canAgree;

    /**
     * 拒绝权限
     */
    private Integer canRefuse;
    /**
     * 退回
     */
    private Integer canReturn;

    /**
     * 撤回
     */
    private Integer canWithdraw;

    /**
     * 作废
     */
    private Integer canInvalid;


    /**
     * 撤销权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer canRevoke;

    /**
     * 打回
     */
    @ApiModelProperty(value = "打回")
    private Integer canRepulse;



    /**
     * 办理别名，默认值为"办理"
     */
    private String dealAlias = "办理";

    /**
     * 同意别名，默认值为"同意"
     */
    private String agreeAlias = "同意";

    /**
     * 撤销别名，默认值为"撤销"
     */
    private String revokeAlias = "撤销";

    /**
     * 拒绝别名，默认值为"拒绝"
     */
    private String refuseAlias = "拒绝";

    /**
     * 退回别名，默认值为"退回"
     */
    private String returnAlias = "退回";

    /**
     * 作废别名，默认值为"作废"
     */
    private String invalidAlias = "作废";

    /**
     * 撤回别名，默认值为"撤回"
     */
    private String withdrawAlias = "撤回";

    /**
     * 打回别名，默认值为"打回"
     */
    private String repulseAlias = "打回";


    /**
     * 办理节点和办理人
     */
    @ApiModelProperty(value = "办理节点和办理人")
    private List<com.qy.workflow.dto.WfNextNodeDTO>  nextNodes;
    /**
     * 退回节点及办理人
     */
    @ApiModelProperty(value = "退回节点及办理人")
    private List<com.qy.workflow.dto.WfNextNodeDTO>  returnNodes;

    /**
     * 打回节点及办理人
     */
    @ApiModelProperty(value = "打回节点及办理人")
    private List<com.qy.workflow.dto.WfNextNodeDTO>  repulseNodes;

    /**
     * 办理表
     */
    private List<com.qy.workflow.dto.WfWfDealTableDTO> tables;


}
