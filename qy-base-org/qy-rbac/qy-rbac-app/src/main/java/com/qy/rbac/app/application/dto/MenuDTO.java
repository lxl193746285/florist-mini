package com.qy.rbac.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 模块id
     */
    @ApiModelProperty("模块id")
    private Long moduleId;

    /**
     * 模块名称
     */
    @ApiModelProperty("模块名称")
    private String moduleName;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 父级名称
     */
    @ApiModelProperty("父级名称")
    private String parentName;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 菜单别名
     */
    @ApiModelProperty("菜单别名")
    private String aliasName;

    /**
     * 功能标示
     */
    @ApiModelProperty("功能标示")
    private String code;

    /**
     * 菜单类型: 普通菜单: 0 权限菜单: 1
     */
    @ApiModelProperty("菜单类型: 普通菜单: 0 权限菜单: 1")
    private Integer typeId;

    /**
     * 菜单类型名称
     */
    @ApiModelProperty("菜单类型名称")
    private String typeName;

    /**
     * 层级
     */
    @ApiModelProperty("层级")
    private Integer level;

    /**
     * 外链
     */
    @ApiModelProperty("外链")
    private String externalLink;

    /**
     * 权限节点
     */
    @ApiModelProperty("权限节点")
    private String authItem;

    /**
     * 权限分类
     */
    @ApiModelProperty("权限分类")
    private String authItemCategory;

    /**
     * 权限分类名称
     */
    @ApiModelProperty("权限分类名称")
    private String authItemCategoryName;

    /**
     * 权限说明
     */
    @ApiModelProperty("权限说明")
    private String authItemExplain;

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
     * 菜单规则
     */
    @ApiModelProperty("菜单规则")
    private List<MenuRuleDTO> rules = new ArrayList<>();


    private Integer authTypeId;

    private String authTypeName;

    /**
     * 子类
     */
    @ApiModelProperty("子类")
    private List<MenuDTO> children;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}