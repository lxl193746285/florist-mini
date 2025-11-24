package com.qy.wf.runWf.dto;

import com.qy.utils.excel.ExcelExport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RunWfListModel {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @ExcelExport(value = "编号")
    private String wfCode;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @ExcelExport(value = "标题")
    private String title;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    @ExcelExport(value = "摘要")
    private String note;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    @ExcelExport(value = "顺序")
    private Integer sort;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @ExcelExport(value = "类型")
    private String type;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    @ExcelExport(value = "流程状态")
    private String statusName;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ExcelExport(value = "流程审批结果")
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private String approveResultName;



    /**
     * 当前节点审批意见
     */
    @ExcelExport(value = "流程审批意见")
    @ApiModelProperty(value = "流程审批意见")
    private String approveComments;

    /**
     * 流程发起部门名称
     */
    @ExcelExport(value = "发起人部门")
    @ApiModelProperty(value = "流程发起部门名称")
    private String beginDeptName;

    /**
     * 流程发起人名称
     */
    @ExcelExport(value = "发起人")
    @ApiModelProperty(value = "流程发起人名称")
    private String beginUserName;

    /**
     * 流程发起时间
     */
    @ExcelExport(value = "发起时间")
    @ApiModelProperty(value = "流程发起时间")
    private String beginTimeName;

    /**
     * 流程结束时间
     */
    @ExcelExport(value = "完成时间")
    @ApiModelProperty(value = "流程结束时间")
    private String endTimeName;

    /**
     * 当前节点ID
     */
    @ExcelExport(value = "当前节点")
    @ApiModelProperty(value = "当前节点名称")
    private String curNodeName;

    /**
     * 节点状态
     */
    @ExcelExport(value = "节点状态")
    @ApiModelProperty(value = "节点状态")
    private String curStatusName;



    /**
     * 节点审批结果
     */
    @ExcelExport(value = "节点审批结果")
    @ApiModelProperty(value = "节点审批结果")
    private String curApproveResultName;


    /**
     *
     */
    @ExcelExport(value = "节点审批意见")
    @ApiModelProperty(value = "节点审批意见")
    private String curApproveComments;

    /**
     * 当前节点办理人发起人
     */
    @ExcelExport(value = "节点办理人")
    @ApiModelProperty(value = "当前节点办理人发起人")
    private String curNodeUserName;

    /**
     * 当前节点发起时间
     */
    @ExcelExport(value = "当前节点发起时间")
    @ApiModelProperty(value = "当前节点发起时间")
    private String curSendTimeName;

    /**
     * 当前节点到达时间
     */
    @ExcelExport(value = "节点到达时间")
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTimeName;


    /**
     * 当前节点阅知时间
     */
    @ExcelExport(value = "节点阅知时间")
    @ApiModelProperty(value = "当前节点阅知时间")
    private String curReadTimeName;

    /**
     * 当前节点办理时间
     */
    @ExcelExport(value = "节点办理时间")
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTimeName;


    /**
     * 创建人
     */
//    @ApiModelProperty(value = "创建人")
//    @ExcelExport(value = "创建人")
//    private String createName;
//
//    /**
//     * 创建时间
//     */
//    @ApiModelProperty(value = "创建时间")
//    @ExcelExport(value = "创建时间")
//    private String createTime;
//
//
//    /**
//     * 更新人
//     */
//    @ApiModelProperty(value = "更新人")
//    @ExcelExport(value = "更新人")
//    private String updatorName;
//
//    /**
//     * 更新时间
//     */
//    @ApiModelProperty(value = "更新时间")
//    @ExcelExport(value = "更新时间")
//    private String updateTimeName;

}
