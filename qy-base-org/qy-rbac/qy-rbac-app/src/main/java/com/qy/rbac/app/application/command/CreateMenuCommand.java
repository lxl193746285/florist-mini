package com.qy.rbac.app.application.command;

import com.qy.rbac.app.application.dto.MenuRuleFormDTO;
import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建菜单命令
 *
 * @author legendjw
 */
@Data
public class CreateMenuCommand {
    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity identity;

    /**
     * 模块id
     */
    @ApiModelProperty("模块id")
    private Long moduleId;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    @NotBlank(message = "请输入菜单名称")
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
     * 权限类型: 数据数据: 1 页面权限: 2
     */
    @ApiModelProperty("权限类型: 数据数据: 1 页面权限: 2")
    private Integer authTypeId;

    /**
     * 权限类型名称
     */
    @ApiModelProperty("权限类型名称")
    private String authTypeName;

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
     * 权限节点前缀
     */
    @ApiModelProperty("权限节点前缀")
    private String authItemPrefix;

    /**
     * 快速创建的子权限菜单
     */
    @ApiModelProperty("快速创建的子权限菜单")
    private List<String> childPermissionMenus = new ArrayList<>();

    /**
     * 菜单规则
     */
    @ApiModelProperty("菜单规则")
    private List<MenuRuleFormDTO> rules = new ArrayList<>();

    /**
     * 是否刷新超管的权限
     */
    @ApiModelProperty("是否刷新超管的权限")
    private boolean refreshRootPermission = true;
}
