package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基本菜单
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 模块id
     */
    private Long moduleId;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 功能标示
     */
    private String code;

    /**
     * url地址
     */
    private String url;

    /**
     * 权限节点
     */
    private String authItem;

    /**
     * 子类
     */
    private List<MenuBasicDTO> children = new ArrayList<>();
}