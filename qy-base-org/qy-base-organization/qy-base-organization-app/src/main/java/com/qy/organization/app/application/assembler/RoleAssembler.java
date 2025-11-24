package com.qy.organization.app.application.assembler;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.RoleBasicDTO;
import com.qy.organization.app.application.dto.RoleDTO;
import com.qy.organization.app.application.security.RolePermission;
import com.qy.organization.app.config.OrganizationConfig;
import com.qy.organization.app.domain.enums.RoleContext;
import com.qy.organization.app.infrastructure.persistence.RoleDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限组汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class RoleAssembler {
    @Autowired
    private RolePermission rolePermission;
    @Autowired
    private CodeTableClient codeTableClient;
    @Autowired
    private RoleDataRepository roleDataRepository;
    @Autowired
    private OrganizationConfig organizationConfig;

    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
//    @Mapping(source = "roleDO", target = "users", qualifiedByName = "mapUsers")
    @Mapping(source = "roleDO", target = "personNum", qualifiedByName = "getPersonNum")
    @Mapping(source = "defaultRole", target = "defaultRoleName", qualifiedByName = "mapDefaultRoleName")
    @Mapping(source = "roleDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public RoleDTO toDTO(RoleDO roleDO, @Context List<OrganizationBasicDTO> organizationDTOS, @Context Identity identity);

    abstract public RoleBasicDTO toBasicDTO(RoleDO roleDO);

    @Named("mapOrganizationName")
    public String mapOrganizationName(Long organizationId, @Context List<OrganizationBasicDTO> organizationDTOS) {
        if (organizationDTOS == null) { return ""; }
        return organizationDTOS.stream().filter(p -> p.getId().equals(organizationId)).findFirst().map(
                o -> StringUtils.isNotBlank(o.getShortName()) ? o.getShortName() : o.getName()
        ).orElse("");
    }

    @Named("mapDefaultRoleName")
    public String mapDefaultRoleName(String defaultRole) {
        if (StringUtils.isBlank(defaultRole)) { return ""; }
        return codeTableClient.getSystemCodeTableItemName("default_role", defaultRole);
    }

//    @Named("mapUsers")
//    public List<RoleUserDTO> mapUsers(RoleDO roleDO) {
////        List<UserRoleDO> userRoleDOS = roleDataRepository.findRoleUsers(roleDO.getId());
//        List<UserRoleDO> userRoleDOS = roleDataRepository.findRoleUsersForSh(roleDO.getId());
//        return userRoleDOS.isEmpty() ? new ArrayList<>() : userRoleDOS.stream().map(u -> new RoleUserDTO(u.getUserId(), u.getUserName())).collect(Collectors.toList());
//    }

    @Named("getPersonNum")
    public Integer getPersonNum(RoleDO roleDO) {
        Integer personCount = roleDataRepository.findRoleUsersForShCount(roleDO.getId());
        return personCount;
    }

    @Named("mapActions")
    protected List<Action> mapActions(RoleDO roleDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (rolePermission.hasPermission(identity, RolePermission.AUTHORIZE, roleDO.getId())) {
            actions.add(RolePermission.AUTHORIZE.toAction());
        }
//        if ((roleDO.getContext().equals(RoleContext.SUBORDINATE.getId()) || (roleDO.getContext().equals(RoleContext.MEMBER_SYSTEM.getId()) ))&& rolePermission.hasPermission(identity, RolePermission.SET_DEFAULT_ROLE, roleDO.getId())) {
//            actions.add(RolePermission.SET_DEFAULT_ROLE.toAction());
//        }
//        if (roleDO.getContext().equals(RoleContext.MEMBER_SYSTEM.getId()) && rolePermission.hasPermission(identity, RolePermission.MEMBER_SET_DEFAULT_ROLE, roleDO.getId())) {
//            actions.add(RolePermission.MEMBER_SET_DEFAULT_ROLE.toAction());
//        }
        if (!roleDO.getContextId().equals(organizationConfig.arkSystemId) &&
                rolePermission.hasPermission(identity, RolePermission.SET_DEFAULT_ROLE, roleDO.getId())) {
            actions.add(RolePermission.SET_DEFAULT_ROLE.toAction());
        }
        if (identity.hasPermission(RolePermission.CREATE)) {
            actions.add(RolePermission.COPY.toAction());
        }
        if (rolePermission.hasPermission(identity, RolePermission.UPDATE, roleDO.getId())) {
            actions.add(RolePermission.UPDATE.toAction());
        }
        if (rolePermission.hasPermission(identity, RolePermission.DELETE, roleDO.getId())) {
            actions.add(RolePermission.DELETE.toAction());
        }
        return actions;
    }
}