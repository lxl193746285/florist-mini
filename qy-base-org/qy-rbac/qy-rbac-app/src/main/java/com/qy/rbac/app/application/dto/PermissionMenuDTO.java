package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限菜单
 *
 * @author legendjw
 */
@Data
public class PermissionMenuDTO {
    /**
     * id
     */
    private Long id;

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
     * 认证项
     */
    private String authItem;

    /**
     * 权限说明
     */
    private String authItemExplain;

    /**
     * 菜单规则
     */
    private List<RuleScopeBasicDTO> rules = new ArrayList<>();
}
