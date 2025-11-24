package com.qy.jpworkflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发起工作流
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowCaseStepUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer caseStepId;
    private Integer nodeId;
    private Integer userId;
    private Integer isRead;
    private Byte sort;
    private Integer read_time;
    private Integer create_time;



}
