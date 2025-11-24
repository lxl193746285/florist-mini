package com.tencent.wxcloudrun.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 管理员扩展信息实体类
 */
@Data
public class Admin {
    /**
     * 管理员ID
     */
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 工号
     */
    private String employeeNo;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 职位
     */
    private String position;

    /**
     * 是否超级管理员：0-否，1-是
     */
    private Integer isSuperAdmin;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
