package com.qy.rbac.app.domain.menu;

import com.qy.ddd.interfaces.Entity;
import com.qy.rbac.app.application.command.UpdateMenuCommand;
import com.qy.rbac.app.domain.share.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限菜单
 *
 * @author legendjw
 */
@Getter
@Builder
public class PermissionMenu implements Entity {
    /**
     * id
     */
    private MenuId id;
    /**
     * 模块id
     */
    private ModuleId moduleId;
    /**
     * 父级id
     */
    private MenuId parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单别名
     */
    private String aliasName;
    /**
     * 认证项
     */
    private String authItem;
    /**
     * 权限分类
     */
    private String authItemCategory;
    /**
     * 权限说明
     */
    private String authItemExplain;
    /**
     * 功能标示
     */
    private String code;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 额外数据
     */
    private String extraData;
    /**
     * 图标
     */
    private String icon;
    /**
     * 状态
     */
    private ShowHiddenStatus status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
    /**
     * 菜单规则
     */
    private List<MenuRule> rules;
    /**
     * 创建人
     */
    private User creator;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private User updator;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 外链
     */
    private String externalLink;

    /**
     * 权限类型
     */
    private Integer typeId;

    /**
     * 权限类型: 行数据: 1 页面权限: 2
     */
    @ApiModelProperty("权限类型: 行数据: 1 页面权限: 2")
    private Integer authTypeId;

    /**
     * 权限类型名称
     */
    @ApiModelProperty("权限类型名称")
    private String authTypeName;

    /**
     * 修改菜单信息
     *
     * @param command
     */
    public void modifyInfo(UpdateMenuCommand command) {
        this.moduleId = new ModuleId(command.getModuleId());
        this.parentId = new MenuId(command.getParentId());
        this.name = command.getName();
        this.aliasName = command.getAliasName();
        this.code = command.getCode();
        this.level = command.getLevel();
        this.authItem = command.getAuthItem();
        this.authItemCategory = command.getAuthItemCategory();
        this.authItemExplain = command.getAuthItemExplain();
        this.extraData = command.getExtraData();
        this.icon = command.getIcon();
        this.status = ShowHiddenStatus.getById(command.getStatusId());
        this.sort = command.getSort();
        this.remark = command.getRemark();
        this.updator = new User(command.getIdentity().getId(), command.getIdentity().getName());
        this.updateTime = LocalDateTime.now();
        this.externalLink = command.getExternalLink();
        this.authTypeId = command.getAuthTypeId();
        this.authTypeName = command.getAuthTypeName();
    }

    /**
     * 修改规则
     *
     * @param rules
     */
    public void modifyRules(List<MenuRule> rules) {
        this.rules = rules;
    }
}