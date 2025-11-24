package com.qy.organization.api.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 更新员工命令
 *
 * @author legendjw
 */
@Data
public class UpdateEmployeeCommand {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 部门id
     */
    @NotNull(message = "请选择部门")
    private Long departmentId;

    /**
     * 岗位id集合
     */
    private List<Long> jobIds;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入员工姓名")
    private String name;

    /**
     * 头像附件id
     */
    private Long avatarAttachmentId;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入员工手机号")
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
     * 工号
     */
    private String number;

    /**
     * 职称
     */
    private String jobTitle;

    /**
     * 直属领导id
     */
    private Long supervisorId;

    /**
     * 直属领导名称
     */
    private String supervisorName;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 是否在通讯录显示: 1: 显示 0: 隐藏
     */
    private Byte isShowBook;

    /**
     * 入职时间
     */
    private LocalDate entryTime;

    /**
     * 转正时间
     */
    private LocalDate conversionTime;

    /**
     * 离职时间
     */
    private LocalDate leaveTime;

    /**
     * 离职原因id
     */
    private Integer leaveReasonId;

    /**
     * 离职原因名称
     */
    private String leaveReasonName;

    /**
     * 离职文件
     */
    private List<Long> leaveFiles;

    /**
     * 离职经办人id
     */
    private Long leaveOperatorId;

    /**
     * 离职经办人名称
     */
    private String leaveOperatorName;

    /**
     * 离职交接内容
     */
    private String leaveHandoverContent;

    /**
     * 离职备注
     */
    private String leaveRemark;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 婚否
     */
    private String maritalStatus;

    /**
     * 护照号
     */
    private String passportNo;

    /**
     * 内部任职经历
     */
    private String internalExperience;

    /**
     * 从业经历
     */
    private String workingExperience;

    /**
     * 紧急联系人
     */
    private String emergencyContactPerson;

    /**
     * 紧急联系方式
     */
    private String emergencyContact;

    /**
     * 紧急联系人与本人关系
     */
    private String emergencyContactRelationship;

    /**
     * 家庭成员
     */
    private String memberOfFamily;

    /**
     * 教育经历
     */
    private String educationExperience;

    /**
     * 省份代码
     */
    private Integer provinceId;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市代码
     */
    private Integer cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 地区代码
     */
    private Integer areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 街道代码
     */
    private Integer streetId;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;


    /**
     * 新增类型（0:蓝牙员工新增,1:人事员工新增）
     */
    private Integer createType;


    /**
     * 员工在职状态 1 在职 0离职
     */
    private Integer jobStatusId;

    /**
     * 员工在职状态 1 在职 0离职
     */
    private String jobStatusName;

    private Long userId;
}
