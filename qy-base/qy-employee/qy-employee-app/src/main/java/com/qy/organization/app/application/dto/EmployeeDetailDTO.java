package com.qy.organization.app.application.dto;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织员工
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Data
public class EmployeeDetailDTO implements Serializable {
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
    @ApiModelProperty("分类id")
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
     * 入职时间
     */
    @ApiModelProperty("入职时间")
    private LocalDate entryTime;

    /**
     * 转正时间
     */
    @ApiModelProperty("转正时间")
    private LocalDate conversionTime;

    /**
     * 离职时间
     */
    @ApiModelProperty("离职时间")
    private LocalDate leaveTime;

    /**
     * 离职原因id
     */
    @ApiModelProperty("离职原因id")
    private Integer leaveReasonId;

    /**
     * 离职原因名称
     */
    @ApiModelProperty("离职原因名称")
    private String leaveReasonName;

    /**
     * 离职文件
     */
    @ApiModelProperty("离职文件")
    private List<AttachmentBasicDTO> leaveFiles;

    /**
     * 离职经办人id
     */
    @ApiModelProperty("离职经办人id")
    private Long leaveOperatorId;

    /**
     * 离职经办人名称
     */
    @ApiModelProperty("离职经办人名称")
    private String leaveOperatorName;

    /**
     * 离职交接内容
     */
    @ApiModelProperty("离职交接内容")
    private String leaveHandoverContent;

    /**
     * 离职备注
     */
    @ApiModelProperty("离职备注")
    private String leaveRemark;

    /**
     * QQ
     */
    @ApiModelProperty("QQ")
    private String qq;

    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    private String wechat;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idNumber;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private LocalDate birthday;

    /**
     * 民族
     */
    @ApiModelProperty("民族")
    private String nation;

    /**
     * 政治面貌
     */
    @ApiModelProperty("政治面貌")
    private String politicalStatus;

    /**
     * 婚否
     */
    @ApiModelProperty("婚否")
    private String maritalStatus;

    /**
     * 护照号
     */
    @ApiModelProperty("护照号")
    private String passportNo;

    /**
     * 内部任职经历
     */
    @ApiModelProperty("内部任职经历")
    private String internalExperience;

    /**
     * 从业经历
     */
    @ApiModelProperty("从业经历")
    private String workingExperience;

    /**
     * 紧急联系人
     */
    @ApiModelProperty("紧急联系人")
    private String emergencyContactPerson;

    /**
     * 紧急联系方式
     */
    @ApiModelProperty("紧急联系方式")
    private String emergencyContact;

    /**
     * 紧急联系人与本人关系
     */
    @ApiModelProperty("紧急联系人与本人关系")
    private String emergencyContactRelationship;

    /**
     * 家庭成员
     */
    @ApiModelProperty("家庭成员")
    private String memberOfFamily;

    /**
     * 教育经历
     */
    @ApiModelProperty("教育经历")
    private String educationExperience;

    /**
     * 省份代码
     */
    @ApiModelProperty("省份代码")
    private Integer provinceId;

    /**
     * 省份名称
     */
    @ApiModelProperty("省份名称")
    private String provinceName;

    /**
     * 城市代码
     */
    @ApiModelProperty("城市代码")
    private Integer cityId;

    /**
     * 城市名称
     */
    @ApiModelProperty("城市名称")
    private String cityName;

    /**
     * 地区代码
     */
    @ApiModelProperty("地区代码")
    private Integer areaId;

    /**
     * 地区名称
     */
    @ApiModelProperty("地区名称")
    private String areaName;

    /**
     * 街道代码
     */
    @ApiModelProperty("街道代码")
    private Integer streetId;

    /**
     * 街道名称
     */
    @ApiModelProperty("街道名称")
    private String streetName;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String address;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

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
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}