package com.qy.message.app.application.assembler;

import com.qy.message.app.application.dto.PlatformConfig;
import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.security.PlatformPermission;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.PlatformDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息平台汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class PlatformAssembler {
    @Autowired
    private PlatformPermission platformPermission;
    @Autowired
    private ObjectMapper objectMapper;

    @Mapping(source = "platformDO", target = "config", qualifiedByName = "mapConfig")
    @Mapping(source = "platformDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "platformDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "platformDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract PlatformDTO toDTO(
            PlatformDO platformDO,
            @Context Identity identity
    );

    @Named("mapActions")
    protected List<Action> mapActions(PlatformDO platformDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (identity.hasPermission(PlatformPermission.VIEW)) {
            actions.add(PlatformPermission.VIEW.toAction());
        }
        if (identity.hasPermission(PlatformPermission.UPDATE)) {
            actions.add(PlatformPermission.UPDATE.toAction());
        }
        if (identity.hasPermission(PlatformPermission.DELETE)) {
            actions.add(PlatformPermission.DELETE.toAction());
        }
        return actions;
    }

    @Named("mapConfig")
    protected PlatformConfig mapPlatformConfig(PlatformDO platformDO, @Context Identity identity) {
        try {
            return objectMapper.readValue(platformDO.getConfig(), PlatformConfig.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}