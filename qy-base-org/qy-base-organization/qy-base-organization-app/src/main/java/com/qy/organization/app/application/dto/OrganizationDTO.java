package com.qy.organization.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织DTO
 *
 * @author legendjw
 */
@Data
public class OrganizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
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
     * 组织电话
     */
    @ApiModelProperty("组织电话")
    private String tel;

    /**
     * 组织联系人
     */
    @ApiModelProperty("组织联系人")
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
     * 组织行业id
     */
    @ApiModelProperty("组织行业id")
    private Integer industryId;

    /**
     * 组织行业名称
     */
    @ApiModelProperty("组织行业名称")
    private String industryName;

    /**
     * 组织规模id
     */
    @ApiModelProperty("组织规模id")
    private Integer scaleId;

    /**
     * 组织规模名称
     */
    @ApiModelProperty("组织规模名称")
    private String scaleName;

    /**
     * 状态id
     */
    @ApiModelProperty("状态id")
    private Integer statusId;

    /**
     * 状态名称
     */
    @ApiModelProperty("状态名称")
    private String statusName;

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

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long creatorId;

    /**
     * 创建人名称
     */
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTimeName;

    /**
     * 更新人id
     */
    @ApiModelProperty("更新人id")
    private Long updatorId;

    /**
     * 更新人名称
     */
    @ApiModelProperty("更新人名称")
    private String updatorName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTimeName;

    /**
     * 操作
     */
    @ApiModelProperty("操作")
    private List<Action> actions = new ArrayList<>();

    /**
     * 权限
     */
    @ApiModelProperty("权限")
    private List<SystemRoleDTO> roles = new ArrayList<>();

    /**
     * 开户状态id
     */
    private Integer openStatusId;

    /**
     * 开户状态名称
     */
    private String openStatusName;

    /**
     * 超管电话
     */
    @ApiModelProperty("超管电话")
    private String accountPhone;

    /**
     * 超管姓名
     */
    @ApiModelProperty("超管姓名")
    private String accountNickname;

}