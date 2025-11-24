package com.qy.crm.customer.app.application.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户DTO
 *
 * @author legendjw
 * @since 2021-08-03
 */
@Data
public class CustomerDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 菜单名称
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
     * 客户等级id
     */
    private Integer levelId;

    /**
     * 客户等级名称
     */
    private String levelName;

    /**
     * 客户来源id
     */
    private Integer sourceId;

    /**
     * 客户来源名称
     */
    private String sourceName;

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
     * 组织行业id
     */
    private Integer industryId;

    /**
     * 组织行业名称
     */
    private String industryName;

    /**
     * 组织规模id
     */
    private Integer scaleId;

    /**
     * 组织规模名称
     */
    private String scaleName;

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

    /**
     * 联系人
     */
    private List<ContactDTO> contacts = new ArrayList<>();

    /**
     * 营业执照
     */
    private BusinessLicenseDTO businessLicense;

    /**
     * 开户行
     */
    private List<OpenBankDTO> openBanks = new ArrayList<>();
}