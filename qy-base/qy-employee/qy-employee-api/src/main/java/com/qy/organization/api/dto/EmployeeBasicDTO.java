package com.qy.organization.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 组织员工基本信息
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Data
public class EmployeeBasicDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 岗位
     */
    private List<JobBasicDTO> jobs;

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

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别id: 1: 男性 0: 女性
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 员工类别（1：盛昊新增）
     */
    private Integer sourceType;

    /**
     * 组织身份类型:0:无权限
     */
    private Integer identityTypeId;

    /**
     * 在职状态id: 1: 在职 0: 离职
     */
    private Integer jobStatusId;

    /**
     * 工号
     */
    private String number;

}