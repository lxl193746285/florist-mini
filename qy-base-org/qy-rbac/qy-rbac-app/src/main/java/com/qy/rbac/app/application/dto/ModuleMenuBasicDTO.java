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
public class ModuleMenuBasicDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 父级id
     */
    private Long parentId = 0L;

    /**
     * 名称
     */
    private String name;

    /**
     * 功能标示
     */
    private String code;

    /**
     * 子项菜单
     */
    private List<MenuBasicDTO> children = new ArrayList<>();
}
