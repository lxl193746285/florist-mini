package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户信息视图对象 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型：1-管理员，2-会员
     */
    private Integer userType;

    /**
     * 用户类型名称
     */
    private String userTypeName;

    /**
     * 手机号（脱敏）
     */
    private String phone;

    /**
     * 邮箱（脱敏）
     */
    private String email;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> permissions;

    // ============ 管理员特有字段 ============
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
     * 是否超级管理员
     */
    private Boolean isSuperAdmin;

    // ============ 会员特有字段 ============
    /**
     * 会员编号
     */
    private String memberNo;

    /**
     * 会员等级
     */
    private Integer level;

    /**
     * 会员等级名称
     */
    private String levelName;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 账户余额
     */
    private BigDecimal balance;
}
