package com.tencent.wxcloudrun.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 权限/菜单实体
 */
@Data
public class Permission {
    /**
     * 权限ID
     */
    private Long id;

    /**
     * 父级权限ID（0为顶级）
     */
    private Long parentId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限类型：1-菜单，2-按钮，3-接口
     */
    private Integer permissionType;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
