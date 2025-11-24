package com.qy.organization.api.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 会员id
     */
    @ApiModelProperty("会员id")
    private Long memberId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 性别id: 1: 男性 0: 女性
     */
    @ApiModelProperty("性别id: 1: 男性 0: 女性")
    private Integer genderId;

    /**
     * 性别名称
     */
    @ApiModelProperty("性别名称")
    private String genderName;

    /**
     * 组织身份类型:0:无权限
     */
    @ApiModelProperty("组织身份类型:0:无权限")
    private Integer identityTypeId;

    /**
     * 在职状态id: 1: 在职 0: 离职
     */
    @ApiModelProperty("在职状态id: 1: 在职 0: 离职")
    private Integer jobStatusId;

    /**
     * 工号
     */
    private String number;

}