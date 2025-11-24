package com.qy.organization.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 员工离职命令
 *
 * @author legendjw
 */
@Data
public class EmployeeLeaveJobCommand implements Serializable {
    /**
     * 员工id
     */
    @ApiModelProperty("员工id")
    private Long id;

    /**
     * 离职时间 格式：2020-01-01
     */
    @ApiModelProperty("离职时间 格式：2020-01-01")
    private String leaveTime;

    /**
     * 离职原因id
     */
    @ApiModelProperty("离职原因id")
    private Integer leaveReasonId;

    /**
     * 离职原因名称
     */
    @ApiModelProperty("离职原因名称")
    private String leaveReasonName;

    /**
     * 离职文件
     */
    @ApiModelProperty("离职文件")
    private List<Long> leaveFiles;

    /**
     * 离职经办人id
     */
    @ApiModelProperty("离职经办人id")
    private Long leaveOperatorId;

    /**
     * 离职经办人名称
     */
    @ApiModelProperty("离职经办人名称")
    private String leaveOperatorName;

    /**
     * 离职交接内容
     */
    @ApiModelProperty("离职交接内容")
    private String leaveHandoverContent;

    /**
     * 离职备注
     */
    @ApiModelProperty("离职备注")
    private String leaveRemark;
}