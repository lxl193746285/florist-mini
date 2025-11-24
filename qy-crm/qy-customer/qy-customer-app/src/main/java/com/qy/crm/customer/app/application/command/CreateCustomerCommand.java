package com.qy.crm.customer.app.application.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建客户命令
 *
 * @author legendjw
 */
@Data
public class CreateCustomerCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 客户名称
     */
    @NotBlank(message = "请输入客户名称")
    private String name;

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
     * 客户等级id 代码表：oa_customer_category
     */
    private Integer levelId;

    /**
     * 客户等级名称
     */
    private String levelName;

    /**
     * 客户来源id 代码表：oa_customer_source
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
     * 排序
     */
    private Integer sort;

    /**
     * 组织行业id 代码表：organization_industry
     */
    private Integer industryId;

    /**
     * 组织行业名称
     */
    private String industryName;

    /**
     * 组织规模id 代码表: organization_scale
     */
    private Integer scaleId;

    /**
     * 组织规模名称
     */
    private String scaleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 联系人
     */
    private List<ContactForm> contacts = new ArrayList<>();

    /**
     * 营业执照
     */
    private BusinessLicenseForm businessLicense;

    /**
     * 开户行
     */
    private List<OpenBankForm> openBanks = new ArrayList<>();
}