package com.qy.wf.runWf.dto;

import com.qy.utils.excel.ExcelExport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RunWfModel {

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
     * 流程审批结果
     */
    @ApiModelProperty(value = "流程审批结果")
    @ExcelExport(value = "流程审批结果")
    private String approveResultName;

    /**
     * 流程审批意见
     */
    @ApiModelProperty(value = "流程审批意见")
    @ExcelExport(value = "流程审批意见")
    private String approveComments;

    /**
     * 发起人部门
     */
    @ApiModelProperty(value = "发起人部门")
    @ExcelExport(value = "发起人部门")
    private String userDeptName;

    /**
     * 发起人
     */
    @ApiModelProperty(value = "发起人")
    @ExcelExport(value = "发起人")
    private String userName;

    /**
     * 发起时间
     */
    @ApiModelProperty(value = "发起时间")
    @ExcelExport(value = "发起时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @ExcelExport(value = "结束时间")
    private String endTime;




    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @ExcelExport(value = "创建人")
    private String createName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @ExcelExport(value = "创建时间")
    private String createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @ExcelExport(value = "更新人")
    private String updatorName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @ExcelExport(value = "更新时间")
    private String updateTimeName;


}
