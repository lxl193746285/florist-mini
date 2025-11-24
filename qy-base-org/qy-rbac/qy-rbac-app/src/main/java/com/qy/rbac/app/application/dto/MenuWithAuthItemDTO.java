package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 包含授权项目的菜单
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuWithAuthItemDTO implements Serializable {
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
     * 授权项
     */
    private String authItem;
}