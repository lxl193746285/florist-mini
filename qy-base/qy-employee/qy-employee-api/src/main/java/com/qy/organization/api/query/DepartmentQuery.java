package com.qy.organization.api.query;

import lombok.Data;

import java.util.List;

/**
 * 部门查询
 *
 * @author legendjw
 */
@Data
public class DepartmentQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织id集合
     */
    private List<Long> organizationIds;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 父级部门
     */
    private Long parentId;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人id集合
     */
    private List<Long> creatorIds;

    /**
     * 权限节点
     */
    private String permission;
}