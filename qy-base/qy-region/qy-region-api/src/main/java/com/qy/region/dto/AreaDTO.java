package com.qy.region.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 地区
 *
 * @author legendjw
 * @since 2021-08-26
 */
@Data
public class AreaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 地区id
     */
    private Integer id;

    /**
     * 父级地区id
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;
}
