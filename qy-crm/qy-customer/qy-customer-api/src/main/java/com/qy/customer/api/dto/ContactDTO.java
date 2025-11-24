package com.qy.customer.api.dto;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户联系人
 *
 * @author legendjw
 * @since 2021-08-03
 */
@Data
public class ContactDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
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
    private AttachmentBasicDTO idNumberFront;

    /**
     * 身份证号反面照
     */
    private AttachmentBasicDTO idNumberBack;

    /**
     * 身份证号有效期开始日期
     */
    private LocalDate idNumberPeriodStart;

    /**
     * 身份证号有效期结束日期
     */
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
    private String createTimeName;

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
    private String updateTimeName;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
