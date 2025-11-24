package com.qy.codetable.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基本代码表细项
 *
 * @author legendjw
 */
@Data
public class CodeTableItemBasicDTO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private String parentId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 值
     */
    private String value;

    /**
     * 扩展数据
     */
    private String extendData;
}
