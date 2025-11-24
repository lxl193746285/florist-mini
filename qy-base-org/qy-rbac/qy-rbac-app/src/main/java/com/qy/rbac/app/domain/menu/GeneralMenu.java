package com.qy.rbac.app.domain.menu;

import com.qy.ddd.interfaces.Entity;
import com.qy.rbac.app.application.command.UpdateMenuCommand;
import com.qy.rbac.app.domain.share.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 普通菜单
 *
 * @author legendjw
 */
@Getter
@Builder
public class GeneralMenu implements Entity {
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
     * 功能标示
     */
    private String code;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 外链
     */
    private String externalLink;
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
     * 修改菜单信息
     *
     * @param command
     */
    public void modifyInfo(UpdateMenuCommand command) {
        ModuleId moduleId = new ModuleId(command.getModuleId());
        //如果菜单修改了模块，则默认作为顶级菜单
        if (moduleId.equals(this.moduleId)) {
            this.parentId = new MenuId(command.getParentId());
        }
        else {
            this.parentId = new MenuId(0L);
        }
        this.moduleId = moduleId;

        this.name = command.getName();
        this.aliasName = command.getAliasName();
        this.code = command.getCode();
        this.level = command.getLevel();
        this.externalLink = command.getExternalLink();
        this.extraData = command.getExtraData();
        this.icon = command.getIcon();
        this.status = ShowHiddenStatus.getById(command.getStatusId());
        this.sort = command.getSort();
        this.remark = command.getRemark();
        this.updator = new User(command.getIdentity().getId(), command.getIdentity().getName());
        this.updateTime = LocalDateTime.now();
    }


}