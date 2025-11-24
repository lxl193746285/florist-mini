package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块菜单
 *
 * @author legendjw
 */
@Data
public class ModuleMenuDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 模块标示
     */
    private String code;

    /**
     * 菜单
     */
    private List<MenuWithRuleDTO> menus = new ArrayList<>();
}
