package com.qy.rbac.app.application.assembler;

import com.qy.member.api.client.MemberSystemClient;
import com.qy.member.api.dto.MemberSystemBasicDTO;
import com.qy.rbac.app.application.dto.ClientBasicDTO;
import com.qy.rbac.app.application.dto.ClientDTO;
import com.qy.rbac.app.application.security.ClientPermission;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
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
 * 客户端汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class ClientAssembler {
    @Autowired
    private MemberSystemClient memberSystemClient;


    @Mapping(source = "clientDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "clientDO", target = "systemName", qualifiedByName = "mapSystemName")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public ClientDTO toDTO(ClientDO clientDO, @Context Identity identity);

    abstract public ClientBasicDTO toBasicDTO(ClientDO clientDO);

    @Named("mapActions")
    public List<Action> mapActions(ClientDO clientDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (identity.hasPermission(ClientPermission.UPDATE)) {
            actions.add(ClientPermission.UPDATE.toAction());
        }
        if (identity.hasPermission(ClientPermission.DELETE)) {
            actions.add(ClientPermission.DELETE.toAction());
        }
        return actions;
    }

    @Named("mapSystemName")
    public String mapSystemName(ClientDO clientDO) {
        MemberSystemBasicDTO memberSystemBasicDTO = memberSystemClient.getBasicMemberSystemById(clientDO.getSystemId());
        if (memberSystemBasicDTO == null){
            return "";
        }
        return memberSystemBasicDTO.getName();
    }

}