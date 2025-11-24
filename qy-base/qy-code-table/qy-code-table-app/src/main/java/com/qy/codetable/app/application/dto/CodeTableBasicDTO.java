package com.qy.codetable.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本代码表
 *
 * @author legendjw
 * @since 2021-07-28
 */
@Data
public class CodeTableBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    private String type;

    /**
     * 标示码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 值类型: NUMBER: 数字 STRING: 字符串
     */
    private String valueType;

    /**
     * 数据结构类型: LIST: 列表 TREE: 树形
     */
    private String structureType;
}