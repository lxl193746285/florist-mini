package com.qy.organization.app.application.dto;

import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织员工
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Data
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String organizationName;

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
     * 岗位
     */
    @ApiModelProperty("岗位")
    private List<JobBasicDTO> jobs;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 姓名拼音
     */
    @ApiModelProperty("姓名拼音")
    private String namePinyin;

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
     * 登录用户名
     */
    @ApiModelProperty("登录用户名")
    private String username;

    /**
     * 登录手机号
     */
    @ApiModelProperty("登录手机号")
    private String userPhone;

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
     * 工号
     */
    @ApiModelProperty("工号")
    private String number;

    /**
     * 职称
     */
    @ApiModelProperty("职称")
    private String jobTitle;

    /**
     * 直属领导id
     */
    @ApiModelProperty("直属领导id")
    private Long supervisorId;

    /**
     * 直属领导名称
     */
    @ApiModelProperty("直属领导名称")
    private String supervisorName;

    /**
     * 在职状态id: 1: 在职 0: 离职
     */
    @ApiModelProperty("在职状态id: 1: 在职 0: 离职")
    private Integer jobStatusId;

    /**
     * 在职状态名称
     */
    @ApiModelProperty("在职状态名称")
    private String jobStatusName;

    /**
     * 分类id
     */
    @ApiModelProperty("分类名称")
    private Integer categoryId;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 是否在通讯录显示: 1: 显示 0: 隐藏
     */
    @ApiModelProperty("是否在通讯录显示: 1: 显示 0: 隐藏")
    private Byte isShowBook;

    /**
     * 组织身份类型id: 0: 员工 1: 创建人 2: 管理员 3: 操作员
     */
    @ApiModelProperty("组织身份类型id: 0: 员工 1: 创建人 2: 管理员 3: 操作员")
    private Integer identityTypeId;

    /**
     * 组织身份类型名称
     */
    @ApiModelProperty("组织身份类型名称")
    private String identityTypeName;

    /**
     * 权限类型id: 1: 权限组 2: 独立权限
     */
    @ApiModelProperty("权限类型id: 1: 权限组 2: 独立权限")
    private Integer permissionTypeId;

    /**
     * 权限类型名称
     */
    @ApiModelProperty("权限类型名称")
    private String permissionTypeName;

    /**
     * 权限组
     */
    @ApiModelProperty("权限组")
    private List<RoleBasicDTO> roles;

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long creatorId;

    /**
     * 创建人名称
     */
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTimeName;

    /**
     * 更新人id
     */
    @ApiModelProperty("更新人id")
    private Long updatorId;

    /**
     * 更新人名称
     */
    @ApiModelProperty("更新人名称")
    private String updatorName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTimeName;

    /**
     * 会员id
     */
    @ApiModelProperty("会员id")
    private String memberId;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}