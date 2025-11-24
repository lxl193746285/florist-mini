package com.qy.organization.app.application.dto;

import lombok.Data;

@Data
public class DeptChirldDTO {
    /**
     * 部门id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门id
     */
    private Long parentId;

    /**
     * 上级部门名称
     */
    private String parentName;
}
