package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类权限菜单
 *
 * @author legendjw
 */
@Data
public class CategoryPermissionMenuDTO {
    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限菜单
     */
    private List<PermissionMenuDTO> permissions = new ArrayList<>();
}
