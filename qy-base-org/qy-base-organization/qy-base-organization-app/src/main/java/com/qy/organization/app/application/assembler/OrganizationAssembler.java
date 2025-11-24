package com.qy.organization.app.application.assembler;

import com.qy.member.api.client.MemberSystemClient;
import com.qy.member.api.dto.MemberSystemBasicDTO;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.OrganizationDTO;
import com.qy.organization.app.application.dto.RoleBasicDTO;
import com.qy.organization.app.application.dto.SystemRoleDTO;
import com.qy.organization.app.application.security.OrganizationPermission;
import com.qy.organization.app.application.service.RoleQueryService;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class OrganizationAssembler {
    @Autowired
    private OrganizationPermission organizationPermission;
    @Autowired
    private EmployeeClient employeeClient;
    @Autowired
    private MemberSystemClient memberSystemClient;
    @Autowired
    @Lazy
    private RoleQueryService roleQueryService;

    @Mapping(source = "organizationDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "organizationDO", target = "accountNickname", qualifiedByName = "mapAccountNickname")
    @Mapping(source = "organizationDO", target = "accountPhone", qualifiedByName = "mapAccountPhone")
    @Mapping(source = "organizationDO", target = "roles", qualifiedByName = "mapRoles")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public OrganizationDTO toDTO(OrganizationDO organizationDO, @Context Identity identity);

    abstract public OrganizationBasicDTO toBasicDTO(OrganizationDO organizationDO);

    @Named("mapActions")
    protected List<Action> mapActions(OrganizationDO organizationDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) {
            return actions;
        }

        if (organizationPermission.hasPermission(identity, OrganizationPermission.VIEW, organizationDO)) {
            actions.add(OrganizationPermission.VIEW.toAction());
        }
        if (organizationPermission.hasPermission(identity, OrganizationPermission.UPDATE, organizationDO) && organizationDO.getParentId() != null
                && organizationDO.getParentId() != 0) {
            actions.add(OrganizationPermission.UPDATE.toAction());
        }
        if (organizationPermission.hasPermission(identity, OrganizationPermission.DELETE, organizationDO) && organizationDO.getParentId() != null
                && organizationDO.getParentId() != 0) {
            actions.add(OrganizationPermission.DELETE.toAction());
        }
        if (organizationPermission.hasPermission(identity, OrganizationPermission.OPEN_ACCOUNT, organizationDO)  && organizationDO.getParentId() != null
                && organizationDO.getParentId() != 0 && organizationDO.getOpenStatusId() != 1 && identity.getId().equals(organizationDO.getCreatorId())) {
            actions.add(OrganizationPermission.OPEN_ACCOUNT.toAction());
        }
        if (organizationPermission.hasPermission(identity, OrganizationPermission.CHANGE_ACCOUNT, organizationDO)  && organizationDO.getParentId() != null
                && organizationDO.getParentId() != 0 && organizationDO.getOpenStatusId() == 1 && identity.getId().equals(organizationDO.getCreatorId())) {
            actions.add(OrganizationPermission.CHANGE_ACCOUNT.toAction());
        }
        return actions;
    }

    @Named("mapAccountNickname")
    protected String mapAccountNickname(OrganizationDO organizationDO) {
        EmployeeBasicDTO employeeBasicDTO = employeeClient.getOrganizationCreator(organizationDO.getId());
        if (employeeBasicDTO == null) {
            return null;
        }
        return employeeBasicDTO.getName();
    }

    @Named("mapAccountPhone")
    protected String mapAccountPhone(OrganizationDO organizationDO) {
        EmployeeBasicDTO employeeBasicDTO = employeeClient.getOrganizationCreator(organizationDO.getId());
        if (employeeBasicDTO == null) {
            return null;
        }
        return employeeBasicDTO.getPhone();
    }

//    @Named("mapRoles")
//    protected List<RoleBasicDTO> mapRoles(OrganizationDO organizationDO) {
//        EmployeeBasicDTO employeeBasicDTO = employeeClient.getOrganizationCreator(organizationDO.getId());
//        if (employeeBasicDTO == null) {
//            return null;
//        }
//        return roleQueryService.getRolesByOrganizationAndUser(employeeBasicDTO.getOrganizationId(), employeeBasicDTO.getId());
//
//    }

    @Named("mapRoles")
    protected List<SystemRoleDTO> mapRoles(OrganizationDO organizationDO) {
        List<SystemRoleDTO> systemRoleDTOList = new ArrayList<>();
        if (organizationDO.getParentId() == 0) {
            return systemRoleDTOList;
        }
        List<MemberSystemBasicDTO> memberSystemBasicDTOList = memberSystemClient.getBasicMemberSystemsByOrganizationIdAndSource(organizationDO.getId(), 2);
        for (MemberSystemBasicDTO memberSystemBasicDTO : memberSystemBasicDTOList) {
            SystemRoleDTO systemRoleDTO = new SystemRoleDTO();
            systemRoleDTO.setSystemId(memberSystemBasicDTO.getId());
            systemRoleDTO.setSystemName(memberSystemBasicDTO.getName());
            EmployeeBasicDTO employeeBasicDTO = employeeClient.getOrganizationCreator(organizationDO.getId());
            if (employeeBasicDTO != null) {
                List<RoleBasicDTO> roleBasicDTOList = roleQueryService.getRolesByOrganizationAndUser(employeeBasicDTO.getOrganizationId(), employeeBasicDTO.getUserId(), memberSystemBasicDTO.getId());
                systemRoleDTO.setRoleIds(roleBasicDTOList);
            }
            systemRoleDTOList.add(systemRoleDTO);
        }
        return systemRoleDTOList;
    }

}