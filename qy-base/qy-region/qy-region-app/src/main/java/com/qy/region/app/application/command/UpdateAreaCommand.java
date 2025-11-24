package com.qy.region.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateAreaCommand {

    @ApiModelProperty("父级编码")
    private Long parentId;

    @ApiModelProperty("编码")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("人口")
    private Integer population;

    @ApiModelProperty("排序")
    private Integer sort;
}
