package com.qy.rbac.app.application.assembler;

import com.qy.rbac.app.application.dto.AppBasicDTO;
import com.qy.rbac.app.application.dto.AppDTO;
import com.qy.rbac.app.application.dto.ClientBasicDTO;
import com.qy.rbac.app.application.security.AppPermission;
import com.qy.rbac.app.application.service.ClientQueryService;
import com.qy.rbac.app.infrastructure.persistence.AppClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppClientDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
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
 * 应用汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class AppAssembler {

    @Autowired
    private AppClientDataRepository appClientDataRepository;
    @Autowired
    private ClientQueryService clientQueryService;

    @Mapping(source = "appDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "appDO", target = "clients", qualifiedByName = "mapClients")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public AppDTO toDTO(AppDO appDO,  @Context Identity identity);

    abstract public AppBasicDTO toBasicDTO(AppDO appDO);

    @Named("mapActions")
    public List<Action> mapActions(AppDO appDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (identity.hasPermission(AppPermission.UPDATE)) {
            actions.add(AppPermission.UPDATE.toAction());
        }
        if (identity.hasPermission(AppPermission.DELETE)) {
            actions.add(AppPermission.DELETE.toAction());
        }
        return actions;
    }

    @Named(("mapClients"))
    public List<ClientBasicDTO> mapClients(AppDO appDO) {
        List<AppClientDO> appClientDOS = appClientDataRepository.findByAppId(appDO.getId());
        if (appClientDOS == null || appClientDOS.isEmpty()){
            return null;
        }
        return clientQueryService.getBasicClientsByIds(appClientDOS.stream().map(AppClientDO::getClientId).collect(Collectors.toList()));
    }
}