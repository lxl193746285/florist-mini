package com.tencent.wxcloudrun.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 管理员注册请求 DTO
 */
@Data
public class AdminRegisterRequest {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    /**
     * 工号
     */
    private String employeeNo;

    /**
     * 部门
     */
    private String department;

    /**
     * 职位
     */
    private String position;

    /**
     * 角色ID列表
     */
    private Long[] roleIds;
}
