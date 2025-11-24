package com.qy.organization.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 组织员工信息
 * </p>
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_org_employee_info")
public class EmployeeInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @TableId(value = "employee_id", type = IdType.ASSIGN_ID)
    private Long employeeId;

    /**
     * 入职时间
     */
    @TableField(value = "entry_time", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDate entryTime;

    /**
     * 转正时间
     */
    @TableField(value = "conversion_time", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDate conversionTime;

    /**
     * 离职时间
     */
    @TableField(value = "leave_time", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
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
    @TableField(value = "birthday", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
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


}
