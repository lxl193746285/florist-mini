package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 创建组织命令
 *
 * @author legendjw
 */
@Data
public class CreateOrganizationCommand implements Serializable {
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "请输入组织名称")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty("简称")
    private String shortName;

    /**
     * 组织logo
     */
    @ApiModelProperty("组织logo")
    private String logo;

    /**
     * 组织电话（开户时为默认管理员手机号）
     */
    @ApiModelProperty("组织电话")
    private String tel;

    /**
     * 组织联系人（开户时为默认管理员名称）
     */
    @ApiModelProperty("联系人")
    private String contactName;

    /**
     * 组织邮箱
     */
    @ApiModelProperty("组织邮箱")
    private String email;

    /**
     * 组织主页
     */
    @ApiModelProperty("组织主页")
    private String homepage;

    /**
     * 组织地址
     */
    @ApiModelProperty("组织地址")
    private String address;

    /**
     * 组织行业
     */
    @ApiModelProperty("组织行业")
    private Integer industryId;

    /**
     * 组织规模
     */
    @ApiModelProperty("组织规模")
    private Integer scaleId;

    /**
     * 父级组织
     */
    @ApiModelProperty("父级组织")
    private Long parentId;

//    /**
//     * 超管id
//     */
//    @ApiModelProperty("超管id")
//    @JsonIgnore
//    private Long superAdminId;

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    @JsonIgnore
    private Long creatorId;

    /**
     * 创建人名称
     */
    @ApiModelProperty("创建人名称")
    @JsonIgnore
    private String creatorName;

//    /**
//     * 权限组ID集合
//     */
//    @ApiModelProperty("权限组ID集合")
//    private List<Long> roleIds;

    /**
     * 来源类型
     */
    @ApiModelProperty("来源类型")
    private String source;

    /**
     * 来源类型名称
     */
    @ApiModelProperty("来源类型名称")
    private String sourceName;

    /**
     * 来源关联id
     */
    @ApiModelProperty("来源关联id")
    private Long sourceDataId;

    /**
     * 来源关联名称
     */
    @ApiModelProperty("来源关联名称")
    private String sourceDataName;
}