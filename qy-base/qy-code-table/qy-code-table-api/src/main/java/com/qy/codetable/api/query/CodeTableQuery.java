package com.qy.codetable.api.query;

import lombok.Data;

/**
 * 代码表分类查询
 *
 * @author legendjw
 */
@Data
public class CodeTableQuery {
    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    private String type;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类标示
     */
    private String categoryCode;

    /**
     * 关键字
     */
    private String keywords;
}
