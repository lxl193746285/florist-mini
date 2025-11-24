package com.tencent.wxcloudrun.dto;

import lombok.Data;
import java.util.List;

/**
 * 菜单树节点VO
 */
@Data
public class MenuTreeVO {
    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父级菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单路由路径
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子菜单列表
     */
    private List<MenuTreeVO> children;
}
