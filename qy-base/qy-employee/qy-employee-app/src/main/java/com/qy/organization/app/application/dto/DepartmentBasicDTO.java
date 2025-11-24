package com.qy.organization.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本的组织部门
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Data
public class DepartmentBasicDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;
}
