package com.qy.member.app.application.assembler;

import com.qy.member.app.application.dto.AccountBasicDTO;
import com.qy.member.app.application.dto.AccountDTO;
import com.qy.member.app.application.security.MemberAccountPermission;
import com.qy.member.app.application.security.MemberPermission;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.RoleBasicDTO;
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
 * 账号汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class AccountAssembler {
    @Autowired
    private MemberAccountPermission accountPermission;
    @Autowired
    private RoleManageClient roleManageClient;

    public abstract AccountBasicDTO toBasicDTO(MemberAccountDO accountDO);

    @Mapping(source = "accountDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "accountDO", target = "roles", qualifiedByName = "mapRoles")
    @Mapping(source = "accountDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "accountDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract AccountDTO toDTO(MemberAccountDO accountDO, @Context Identity identity);

    @Named("mapActions")
    protected List<Action> mapActions(MemberAccountDO accountDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (accountPermission.hasPermission(identity, MemberAccountPermission.VIEW, accountDO.getId())) {
            actions.add(MemberPermission.VIEW.toAction());
        }
        if (accountPermission.hasPermission(identity, MemberAccountPermission.STATUS, accountDO.getId())) {
            actions.add(MemberPermission.STATUS.toAction());
        }
        if (accountPermission.hasPermission(identity, MemberAccountPermission.AUTHORIZE, accountDO.getId())) {
            actions.add(MemberPermission.AUTHORIZE.toAction());
        }

        return actions;
    }

    @Named("mapRoles")
    protected List<RoleBasicDTO> mapRoles(MemberAccountDO accountDO) {
        List<RoleBasicDTO> roles = new ArrayList<>();
//        return roleManageClient.getRolesByUser(accountDO.getOrganizationId(), accountDO.getId());
        return roles;
    }
}