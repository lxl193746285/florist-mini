package com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganizationCreator;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 联系人
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_crm_contact")
public class ContactDO implements Serializable, GetOrganizationCreator {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名拼音
     */
    private String namePinyin;

    /**
     * 性别id: 1: 男性 0: 女性
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 电话1
     */
    private String tel;

    /**
     * 电话2
     */
    private String tel2;

    /**
     * 电话3
     */
    private String tel3;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信
     */
    private String weixin;

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
     * 职位
     */
    private String position;

    /**
     * 邮编
     */
    private String postcode;

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
     * 地区代码
     */
    private Integer streetId;

    /**
     * 地区名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 身份证号正面照
     */
    private String idNumberFront;

    /**
     * 身份证号反面照
     */
    private String idNumberBack;

    /**
     * 身份证号有效期开始日期
     */
    @TableField(value = "id_number_period_start", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDate idNumberPeriodStart;

    /**
     * 身份证号有效期结束日期
     */
    @TableField(value = "id_number_period_end", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDate idNumberPeriodEnd;

    /**
     * 是否是法人
     */
    private Byte isLegalPerson;

    /**
     * 是否是超管
     */
    private Byte isSuperAdmin;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
