package com.qy.rbac.app.application.assembler;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.rbac.app.application.dto.AppBasicDTO;
import com.qy.rbac.app.application.dto.ModuleBasicDTO;
import com.qy.rbac.app.application.dto.ModuleDTO;
import com.qy.rbac.app.application.security.ModulePermission;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ModuleDO;
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

/**
 * 模块汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class ModuleAssembler {

    @Mapping(source = "appId", target = "appName", qualifiedByName = "mapAppName")
    @Mapping(source = "moduleDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public ModuleDTO toDTO(ModuleDO moduleDO, @Context List<AppBasicDTO> appBasicDTOS, @Context Identity identity);

    abstract public ModuleBasicDTO toBasicDTO(ModuleDO moduleDO);

    @Named("mapAppName")
    public String mapAppName(Long appId, @Context List<AppBasicDTO> appBasicDTOS) {
        if (appBasicDTOS == null) { return ""; }
        return appBasicDTOS.stream().filter(m -> m.getId().equals(appId)).findFirst().map(AppBasicDTO::getName).orElse("");
    }

    @Named("mapActions")
    public List<Action> mapActions(ModuleDO moduleDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (identity.hasPermission(ModulePermission.UPDATE)) {
            actions.add(ModulePermission.UPDATE.toAction());
        }
        if (identity.hasPermission(ModulePermission.DELETE)) {
            actions.add(ModulePermission.DELETE.toAction());
        }
        return actions;
    }
}