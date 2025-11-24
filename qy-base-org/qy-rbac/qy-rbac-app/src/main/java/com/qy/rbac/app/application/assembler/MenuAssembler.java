package com.qy.rbac.app.application.assembler;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.rbac.app.application.dto.*;
import com.qy.rbac.app.application.enums.CommonPermissionAction;
import com.qy.rbac.app.application.security.MenuPermission;
import com.qy.rbac.app.domain.menu.MenuType;
import com.qy.rbac.app.infrastructure.persistence.MenuDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class MenuAssembler {
    @Autowired
    private MenuDataRepository menuDataRepository;
    @Autowired
    private MenuRuleAssembler menuRuleAssembler;
    @Autowired
    private RuleScopeAssembler ruleScopeAssembler;
    @Autowired
    private CodeTableClient codeTableClient;

    @Mapping(source = "moduleId", target = "moduleName", qualifiedByName = "mapModuleName")
    @Mapping(source = "parentId", target = "parentName", qualifiedByName = "mapParentName")
    @Mapping(source = "authItemCategory", target = "authItemCategoryName", qualifiedByName = "mapAuthItemCategory")
    @Mapping(source = "menuDO", target = "rules", qualifiedByName = "mapRules")
    @Mapping(source = "menuDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public MenuDTO toDTO(
            MenuDO menuDO,
            @Context List<ModuleBasicDTO> moduleBasicDTOS,
            @Context List<MenuDO> parentMenus,
            @Context List<CodeTableItemBasicDTO> authItemCategories,
            @Context Identity identity
    );

    abstract public List<MenuDTO> toDTOs(
            List<MenuDO> menuDOs,
            @Context List<ModuleBasicDTO> moduleBasicDTOS,
            @Context List<MenuDO> parentMenus,
            @Context List<CodeTableItemBasicDTO> authItemCategories,
            @Context Identity identity
    );

    @Mapping(source = "externalLink", target = "url")
    abstract public MenuBasicDTO toBasicDTO(MenuDO menuDO);

    abstract public MenuWithRuleDTO toRuleDTO(MenuDO menuDO);

    @Mapping(source = "menuDO", target = "rules", qualifiedByName = "mapScopeRules")
    abstract public PermissionMenuDTO toPermissionMenu(MenuDO menuDO, @Context List<RuleScopeDO> ruleScopeDOS);

    abstract public List<PermissionMenuDTO> toPermissionMenu(List<MenuDO> menuDOs, @Context List<RuleScopeDO> ruleScopeDOS);

    abstract public List<MenuWithAuthItemDTO> toMenuWithAuthItem(List<MenuDO> menuDOs);

    @Named("mapModuleName")
    public String mapModuleName(Long moduleId, @Context List<ModuleBasicDTO> moduleBasicDTOS) {
        if (moduleBasicDTOS == null) { return ""; }
        return moduleBasicDTOS.stream().filter(m -> m.getId().equals(moduleId)).findFirst().map(ModuleBasicDTO::getName).orElse("");
    }

    @Named("mapParentName")
    public String mapParentName(Long parentId, @Context List<MenuDO> parentMenus) {
        if (parentMenus == null) { return ""; }
        return parentMenus.stream().filter(m -> m.getId().equals(parentId)).findFirst().map(MenuDO::getName).orElse("");
    }

    @Named("mapAuthItemCategory")
    public String mapAuthItemCategory(String authItemCategory, @Context List<CodeTableItemBasicDTO> authItemCategories) {
        if (authItemCategories == null) { return ""; }
        return authItemCategories.stream().filter(m -> m.getId().equals(authItemCategory)).findFirst().map(CodeTableItemBasicDTO::getName).orElse("");
    }

    @Named("mapRules")
    public List<MenuRuleDTO> mapRules(MenuDO menuDO) {
        if (menuDO.getTypeId().intValue() == MenuType.PERMISSION_MENU.getId()) {
            List<MenuRuleDO> menuRuleDOS = menuDataRepository.findMenuRulesByMenuId(menuDO.getId());
            return menuRuleAssembler.toDTO(menuRuleDOS, null);
        }
        else {
            return new ArrayList<>();
        }
    }

    @Named("mapScopeRules")
    public List<RuleScopeBasicDTO> mapScopeRules(MenuDO menuDO, @Context List<RuleScopeDO> ruleScopeDOS) {
        if (ruleScopeDOS == null) { return new ArrayList<>(); }
        List<MenuRuleDO> menuRuleDOS = menuDataRepository.findMenuRulesByMenuId(menuDO.getId());
        return menuRuleDOS.stream().map(m -> {
            RuleScopeDO ruleScopeDO = ruleScopeDOS.stream().filter(r -> r.getId().equals(m.getScopeId())).findFirst().orElse(null);
            return ruleScopeAssembler.toBasicDTO(ruleScopeDO);
        }).collect(Collectors.toList());
    }

    @Named("mapActions")
    public List<Action> mapActions(MenuDO menuDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (menuDO.getTypeId().intValue() == MenuType.GENERAL_MENU.getId() && identity.hasPermission(MenuPermission.CREATE)) {
            actions.add(MenuPermission.CREATE_CHILD.toAction());
        }
        if (identity.hasPermission(MenuPermission.UPDATE) && !menuDO.getAuthItemCategory().equals(CommonPermissionAction.MENU.getId())) {
            actions.add(MenuPermission.UPDATE.toAction());
        }
        if (identity.hasPermission(MenuPermission.DELETE) && !menuDO.getAuthItemCategory().equals(CommonPermissionAction.MENU.getId())) {
            actions.add(MenuPermission.DELETE.toAction());
        }
        return actions;
    }
}