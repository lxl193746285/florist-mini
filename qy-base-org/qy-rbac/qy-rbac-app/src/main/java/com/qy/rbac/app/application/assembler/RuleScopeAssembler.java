package com.qy.rbac.app.application.assembler;

import com.qy.rbac.app.application.dto.RuleScopeBasicDTO;
import com.qy.rbac.app.application.dto.RuleScopeDTO;
import com.qy.rbac.app.application.security.RuleScopePermission;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则范围汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public interface RuleScopeAssembler {
    @Mapping(source = "ruleScopeDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    RuleScopeDTO toDTO(RuleScopeDO ruleScopeDO, @Context Identity identity);

    RuleScopeBasicDTO toBasicDTO(RuleScopeDO ruleScopeDO);

    @Named("mapActions")
    default List<Action> mapActions(RuleScopeDO ruleScopeDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (identity.hasPermission(RuleScopePermission.UPDATE)) {
            actions.add(RuleScopePermission.UPDATE.toAction());
        }
        if (identity.hasPermission(RuleScopePermission.DELETE)) {
            actions.add(RuleScopePermission.DELETE.toAction());
        }
        return actions;
    }
}