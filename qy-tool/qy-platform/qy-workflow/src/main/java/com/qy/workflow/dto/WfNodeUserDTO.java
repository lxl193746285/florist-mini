package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_执行_办理人信息
 *
 * @author iFeng
 * @since 2022-11-21
 */
@Data
public class WfNodeUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     *用户Id
     */
    @ApiModelProperty(value = "用户Id")
    private Long userId;


}
