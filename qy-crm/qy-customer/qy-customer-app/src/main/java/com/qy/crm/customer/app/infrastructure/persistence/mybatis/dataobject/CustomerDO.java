package com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganizationDepartmentCreator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_crm_customer")
public class CustomerDO implements Serializable, GetOrganizationDepartmentCreator {

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
     * 父级id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音
     */
    private String namePinyin;

    /**
     * 电话
     */
    private String tel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 主页
     */
    private String homepage;

    /**
     * 等级id
     */
    private Integer levelId;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 来源id
     */
    private Integer sourceId;

    /**
     * 来源名称
     */
    private String sourceName;

    /**
     * 行业id
     */
    private Integer industryId;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 规模id
     */
    private Integer scaleId;

    /**
     * 规模名称
     */
    private String scaleName;

    /**
     * 所属部门id
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 客户经理id
     */
    private Long accountManagerId;

    /**
     * 客户经理名称
     */
    private String accountManagerName;

    /**
     * 片区经理id
     */
    private Long regionalManagerId;

    /**
     * 片区经理名称
     */
    private String regionalManagerName;

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
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * gps地址
     */
    private String gpsAddress;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 是否已开户 1: 已开户 0: 未开户
     */
    private Byte isOpenAccount;

    /**
     * 开户组织id
     */
    private Long openAccountId;

    /**
     * 是否已开通会员 1: 已开通 0: 未开通
     */
    private Byte isOpenMember;

    /**
     * 开通会员id
     */
    private Long openMemberId;

    /**
     * 排序
     */
    private Integer sort;

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