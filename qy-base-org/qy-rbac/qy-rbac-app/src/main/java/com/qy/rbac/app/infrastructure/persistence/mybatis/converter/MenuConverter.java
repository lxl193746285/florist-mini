package com.qy.rbac.app.infrastructure.persistence.mybatis.converter;

import com.qy.rbac.app.domain.menu.*;
import com.qy.rbac.app.domain.menu.*;
import com.qy.rbac.app.domain.share.User;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 菜单转换器
 *
 * @author legendjw
 */
@Mapper
public interface MenuConverter {
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "moduleId.id", target = "moduleId"),
            @Mapping(source = "parentId.id", target = "parentId"),
            @Mapping(source = "status.id", target = "statusId"),
            @Mapping(source = "status.name", target = "statusName"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
            @Mapping(source = "updator.id", target = "updatorId"),
            @Mapping(source = "updator.name", target = "updatorName"),
    })
    MenuDO generalMenuToDO(GeneralMenu generalMenu);

    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "moduleId.id", target = "moduleId"),
            @Mapping(source = "parentId.id", target = "parentId"),
            @Mapping(source = "status.id", target = "statusId"),
            @Mapping(source = "status.name", target = "statusName"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
            @Mapping(source = "updator.id", target = "updatorId"),
            @Mapping(source = "updator.name", target = "updatorName"),
    })
    MenuDO permissionMenuToDO(PermissionMenu permissionMenu);

    default GeneralMenu toGeneralMenu(MenuDO menuDO) {
        return GeneralMenu.builder()
                .id(new MenuId(menuDO.getId()))
                .moduleId(new ModuleId(menuDO.getModuleId()))
                .parentId(new MenuId(menuDO.getParentId()))
                .name(menuDO.getName())
                .aliasName(menuDO.getAliasName())
                .code(menuDO.getCode())
                .level(menuDO.getLevel())
                .externalLink(menuDO.getExternalLink())
                .icon(menuDO.getIcon())
                .status(ShowHiddenStatus.getById(menuDO.getStatusId()))
                .sort(menuDO.getSort())
                .remark(menuDO.getRemark())
                .creator(new User(menuDO.getCreatorId(), menuDO.getCreatorName()))
                .createTime(menuDO.getCreateTime())
                .updator(new User(menuDO.getUpdatorId(), menuDO.getUpdatorName()))
                .updateTime(menuDO.getUpdateTime())
                .build();
    }

    default PermissionMenu toPermissionMenu(MenuDO menuDO, List<MenuRule> menuRules) {
        return PermissionMenu.builder()
                .id(new MenuId(menuDO.getId()))
                .moduleId(new ModuleId(menuDO.getModuleId()))
                .parentId(new MenuId(menuDO.getParentId()))
                .name(menuDO.getName())
                .aliasName(menuDO.getAliasName())
                .code(menuDO.getCode())
                .level(menuDO.getLevel())
                .authItem(menuDO.getAuthItem())
                .authItemCategory(menuDO.getAuthItemCategory())
                .authItemExplain(menuDO.getAuthItemExplain())
                .icon(menuDO.getIcon())
                .status(ShowHiddenStatus.getById(menuDO.getStatusId()))
                .sort(menuDO.getSort())
                .remark(menuDO.getRemark())
                .rules(menuRules)
                .creator(new User(menuDO.getCreatorId(), menuDO.getCreatorName()))
                .createTime(menuDO.getCreateTime())
                .updator(new User(menuDO.getUpdatorId(), menuDO.getUpdatorName()))
                .updateTime(menuDO.getUpdateTime())
                .build();
    }
}