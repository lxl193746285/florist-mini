package com.qy.organization.app.application.assembler;

import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.DepartmentBasicDTO;
import com.qy.organization.app.application.dto.DepartmentDTO;
import com.qy.organization.app.application.security.DepartmentPermission;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
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
 * 部门汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class DepartmentAssembler {
    @Autowired
    private DepartmentPermission departmentPermission;

    @Mapping(source = "parentId", target = "parentName", qualifiedByName = "mapParentName")
    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "departmentDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract DepartmentDTO toDTO(
            DepartmentDO departmentDO,
            @Context List<DepartmentDO> parentDepartments,
            @Context List<OrganizationBasicDTO> organizationDTOS,
            @Context Identity identity
    );

    public abstract List<DepartmentDTO> toDTOs(
            List<DepartmentDO> departmentDOs,
            @Context List<DepartmentDO> parentDepartments,
            @Context List<OrganizationBasicDTO> organizationDTOS,
            @Context Identity identity
    );

    public abstract DepartmentBasicDTO toBasicDTO(DepartmentDO departmentDO);

    public abstract List<DepartmentBasicDTO> toBasicDTOs(List<DepartmentDO> departmentDOs);

    @Named("mapParentName")
    public String mapParentName(Long parentId, @Context List<DepartmentDO> parentDepartments) {
        if (parentDepartments == null) { return ""; }
        return parentDepartments.stream().filter(p -> p.getId().equals(parentId)).findFirst().map(DepartmentDO::getName).orElse("");
    }

    @Named("mapOrganizationName")
    public String mapOrganizationName(Long organizationId, @Context List<OrganizationBasicDTO> organizationDTOS) {
        if (organizationDTOS == null) { return ""; }
        return organizationDTOS.stream().filter(p -> p.getId().equals(organizationId)).findFirst().map(
                o -> StringUtils.isNotBlank(o.getShortName()) ? o.getShortName() : o.getName()
        ).orElse("");
    }

    @Named("mapActions")
    public List<Action> mapActions(DepartmentDO departmentDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        boolean isTopDepartment = departmentDO.getParentId().intValue() == 0;

        if (departmentPermission.hasPermission(identity, DepartmentPermission.UPDATE, departmentDO.getId())) {
            actions.add(DepartmentPermission.UPDATE.toAction());
        }
        if (!isTopDepartment && departmentPermission.hasPermission(identity, DepartmentPermission.DELETE, departmentDO.getId())) {
            actions.add(DepartmentPermission.DELETE.toAction());
        }

        return actions;
    }
}