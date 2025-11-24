package com.qy.codetable.app.application.query;

import com.qy.rest.pagination.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 代码表分类查询
 *
 * @author legendjw
 */
@Data
public class CodeTableItemQuery extends PageQuery {
    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    @ApiModelProperty("类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码")
    private String type;

    /**
     * 关联id
     */
    @ApiModelProperty("关联id")
    private Long relatedId = 0L;

    /**
     * 标示码
     */
    @ApiModelProperty("标示码")
    private String code;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 状态
     */
    @ApiModelProperty("关键字")
    private Integer status;

    /**
     * 排除项
     */
    @ApiModelProperty("")
    private List<String> excludes;
}
