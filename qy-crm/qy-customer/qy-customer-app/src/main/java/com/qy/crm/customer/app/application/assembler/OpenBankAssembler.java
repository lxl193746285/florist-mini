package com.qy.crm.customer.app.application.assembler;

import com.qy.crm.customer.app.application.dto.OpenBankDTO;
import com.qy.crm.customer.app.application.security.OpenBankPermission;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.OpenBankDO;
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
 * 开户行汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class OpenBankAssembler {
    @Autowired
    private OpenBankPermission openBankPermission;

    public abstract OpenBankDTO toDTO(OpenBankDO openBankDO);

    @Mapping(source = "openBankDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract OpenBankDTO toDTO(OpenBankDO openBankDO, @Context Identity identity);

    @Named("mapActions")
    protected List<Action> mapActions(OpenBankDO openBankDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (openBankPermission.hasPermission(identity, OpenBankPermission.UPDATE, openBankDO.getId())) {
            actions.add(OpenBankPermission.UPDATE.toAction());
        }
        if (openBankPermission.hasPermission(identity, OpenBankPermission.DELETE, openBankDO.getId())) {
            actions.add(OpenBankPermission.DELETE.toAction());
        }
        return actions;
    }
}