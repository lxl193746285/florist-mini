package com.qy.rbac.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class AppDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
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
     * 应用标示
     */
    @ApiModelProperty("应用标示")
    private String code;

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
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 额外数据
     */
    @ApiModelProperty("额外数据")
    private String extraData;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

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
    private List<Action> actions = new ArrayList<>();

    /**
     * 客户端
     */
    @ApiModelProperty("客户端")
    List<ClientBasicDTO> clients = new ArrayList<>();

//    @ApiModelProperty("会员系统id")
//    private Long memberSystemId;
//
//    @ApiModelProperty("会员系统")
//    private String memberSystemName;
}