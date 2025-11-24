package com.qy.codetable.api.dto;

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
    private String id;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
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
