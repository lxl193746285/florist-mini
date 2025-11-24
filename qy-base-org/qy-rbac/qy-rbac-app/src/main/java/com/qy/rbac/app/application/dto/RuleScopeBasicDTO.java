package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 规则范围基本DTO
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class RuleScopeBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否选择数据 1: 是 0: 否
     */
    private Byte isSelectData;

    /**
     * 数据源类型 1: 自定义 2: sql取值
     */
    private Byte dataSource;

    /**
     * 展示形式 1: 多选框 2: 树形多选框
     */
    private Byte selectShowForm;
}