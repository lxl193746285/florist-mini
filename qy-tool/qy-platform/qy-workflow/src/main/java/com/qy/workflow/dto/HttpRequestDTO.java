package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Http请求传入参数
 *
 * @author ifeng
 * @since 2024-10-03
 */
@Data
public class HttpRequestDTO extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "外部传入的token")
    private String token;

    @ApiModelProperty(value = "服务器地址")
    private String serverUrl;
}
