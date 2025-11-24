package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 模块
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class ModuleBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 名称
     */
    private String name;

    /**
     * 模块标示
     */
    private String code;

    /**
     * 状态id
     */
    private Integer statusId;
}