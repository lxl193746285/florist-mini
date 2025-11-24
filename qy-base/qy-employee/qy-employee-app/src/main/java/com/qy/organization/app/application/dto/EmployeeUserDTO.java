package com.qy.organization.app.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 组织员工用户信息
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Data
public class EmployeeUserDTO implements Serializable {
    /**
     * 类型: user: 用户 employee: 员工
     */
    private String type;

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    private Long jobId;

    private String jobName;

    private Long positionId;

    private String positionName;

    private Long deptId;

    private String deptName;
}