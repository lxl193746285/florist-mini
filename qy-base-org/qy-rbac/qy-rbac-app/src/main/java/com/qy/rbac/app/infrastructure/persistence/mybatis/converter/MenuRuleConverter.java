package com.qy.rbac.app.infrastructure.persistence.mybatis.converter;

import com.qy.rbac.app.domain.menu.MenuId;
import com.qy.rbac.app.domain.menu.MenuRule;
import com.qy.rbac.app.domain.menu.MenuRuleId;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 菜单规则转换器
 *
 * @author legendjw
 */
@Mapper
public interface MenuRuleConverter {
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "menuId.id", target = "menuId"),
    })
    MenuRuleDO toDO(MenuRule menuRule);

    default MenuRule toMenuRule(MenuRuleDO menuRuleDO) {
        return MenuRule.builder()
                .id(new MenuRuleId(menuRuleDO.getId()))
                .menuId(new MenuId(menuRuleDO.getMenuId()))
                .name(menuRuleDO.getName())
                .scopeId(menuRuleDO.getScopeId())
                .sort(menuRuleDO.getSort())
                .build();
    }
}