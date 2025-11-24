package com.qy.organization.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织权限组
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String organizationName;

    /**
     * 上下文
     */
    @ApiModelProperty("上下文")
    private String context;

    /**
     * 上下文id
     */
    @ApiModelProperty("上下文id")
    private String contextId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 名称拼音
     */
    @ApiModelProperty("名称拼音")
    private String namePinyin;

    /**
     * 授权项
     */
    @ApiModelProperty("授权项")
    private String authItem;

    /**
     * 岗位描述
     */
    @ApiModelProperty("岗位描述")
    private String description;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 默认权限组类型
     */
    @ApiModelProperty("默认权限组类型")
    private String defaultRole;

    /**
     * 默认权限组类型名称
     */
    @ApiModelProperty("默认权限组类型名称")
    private String defaultRoleName;

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
     * 人员
     */
    @ApiModelProperty("人员")
    private List<RoleUserDTO> users = new ArrayList<>();

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();

    /**
     * 人数
     */
    @ApiModelProperty("人数")
    private Integer personNum;
}
