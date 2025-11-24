package com.qy.organization.app.application.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 创建员工命令
 *
 * @author legendjw
 */
@Data
public class CreateEmployeeCommand {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    @NotNull(message = "请选择组织")
    private Long organizationId;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    @NotNull(message = "请选择部门")
    private Long departmentId;

    /**
     * 岗位id集合
     */
    @ApiModelProperty("岗位id集合")
    private List<Long> jobIds;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank(message = "请输入员工姓名")
    private String name;

    /**
     * 头像附件id
     */
    @ApiModelProperty("头像附件id")
    private Long avatarAttachmentId;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "请输入员工手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 性别id 代码表：gender
     */
    @ApiModelProperty("性别id 代码表：gender")
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
     * 分类id 代码表：user_category
     */
    @ApiModelProperty("分类id 代码表：user_category")
    private Integer categoryId;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 是否在通讯录显示: 代码表：common_yes_or_no
     */
    @ApiModelProperty("是否在通讯录显示: 代码表：common_yes_or_no")
    private Byte isShowBook;

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
    private List<Long> leaveFiles;

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
     * 来源类型（0:蓝牙员工,1:人事员工）
     */
    @ApiModelProperty("来源类型（0:蓝牙员工,1:人事员工）")
    private Integer sourceType;

    /**
     * 新增类型（0:蓝牙员工新增,1:人事员工新增）
     */
    @ApiModelProperty("新增类型（0:蓝牙员工新增,1:人事员工新增）")
    private Integer createType;

    /**
     * 员工在职状态 1 在职 0离职
     */
    @ApiModelProperty("员工在职状态 1 在职 0离职")
    private Integer jobStatusId;

    private String username;

}
