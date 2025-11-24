package com.qy.region.app.application.query;

import lombok.Data;

/**
 * 地区查询
 *
 * @author legendjw
 */
@Data
public class AreaQuery {
    /**
     * 关键字
     */
    private String keywords;

    /**
     * 父级地区id，传递0获取第一级省份
     */
    private Long parentId;

    /**
     * 层级
     */
    private Integer level;

    private Integer page;

    private Integer perPage;
}
