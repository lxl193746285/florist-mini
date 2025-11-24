package com.qy.rbac.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 含有规则的菜单
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuWithRuleDTO implements Serializable {
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
     * 菜单别名
     */
    private String aliasName;

    /**
     * 功能标示
     */
    private String code;

    /**
     * 菜单类型: 普通菜单: 0 权限菜单: 1
     */
    private Integer typeId;

    /**
     * 菜单类型名称
     */
    private String typeName;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 外链
     */
    private String externalLink;

    /**
     * 认证项
     */
    private String authItem;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单规则
     */
    private List<MenuRuleDTO> rules = new ArrayList<>();

    /**
     * 子类
     */
    private List<MenuWithRuleDTO> children = new ArrayList<>();
}