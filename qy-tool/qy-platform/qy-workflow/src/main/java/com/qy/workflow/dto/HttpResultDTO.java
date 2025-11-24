package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口调用返回值结构
 *
 * @author ifeng
 * @since 2024-10-03
 */
@Data
public class HttpResultDTO extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息内容")
    private String  message;

    @ApiModelProperty(value = "状态码")
    private Integer  code;

    @ApiModelProperty(value = "status")
    private boolean  status;

    @ApiModelProperty(value = "数据内容")
    private String data;

}
