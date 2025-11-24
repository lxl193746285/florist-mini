package com.qy.member.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 布尔类型结果
 *
 * @author legendjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoolResultDTO {

    @ApiModelProperty("返回结果")
    private boolean result;
}
