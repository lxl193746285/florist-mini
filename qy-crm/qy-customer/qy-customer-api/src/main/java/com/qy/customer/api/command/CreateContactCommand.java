package com.qy.customer.api.command;

import com.qy.customer.api.dto.RelatedModuleDataDTO;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 创建客户联系人命令
 *
 * @author legendjw
 */
@Data
public class CreateContactCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联信息
     */
    private List<RelatedModuleDataDTO> relatedModuleData;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String name;

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
    private String idNumberFront;

    /**
     * 身份证号反面照
     */
    private String idNumberBack;

    /**
     * 身份证号有效期开始日期
     */
    private LocalDate idNumberPeriodStart;

    /**
     * 身份证号有效期结束日期
     */
    private LocalDate idNumberPeriodEnd;

    /**
     * 备注
     */
    private String remark;
}