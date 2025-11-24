package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 应用
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class AppBasicDTO implements Serializable {
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
     * 名称
     */
    private String name;

    /**
     * 应用标示
     */
    private String code;

    /**
     * 菜单树
     */
    private List<MenuDTO> menuDTOs;
}