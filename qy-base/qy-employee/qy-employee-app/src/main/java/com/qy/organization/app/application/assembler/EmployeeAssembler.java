package com.qy.organization.app.application.assembler;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.security.EmployeePermission;
import com.qy.organization.app.application.service.JobQueryService;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel = "spring")
public abstract class EmployeeAssembler {
    @Value("${qy.organization.ark-system-id}")
    private String arkSystemId;
    @Lazy
    @Autowired
    private JobQueryService jobQueryService;
    @Autowired
    private EmployeePermission employeePermission;
    @Autowired
    private RoleManageClient roleManageClient;
    @Autowired
    private AttachmentClient attachmentClient;

    @Mapping(source = "employeeDO.organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "employeeDO.id", target = "jobs", qualifiedByName = "mapJobs")
    @Mapping(source = "employeeDO", target = "roles", qualifiedByName = "mapRoles")
    @Mapping(source = "employeeDO.departmentId", target = "departmentName", qualifiedByName = "mapDepartmentName")
//    @Mapping(source = "employeeDO.userId", target = "username", qualifiedByName = "mapUsername")
    @Mapping(source = "employeeDO.userId", target = "userPhone", qualifiedByName = "mapUserPhone")
    @Mapping(source = "employeeDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "employeeDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "employeeDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract EmployeeDTO toDTO(
            EmployeeDO employeeDO,
            EmployeeInfoDO employeeInfoDO,
            @Context List<OrganizationBasicDTO> organizationDTOS,
            @Context List<DepartmentBasicDTO> departmentBasicDTOS,
            @Context List<UserBasicDTO> userBasicDTOS,
            @Context Identity identity
    );

    @Mapping(source = "employeeDO.organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "employeeDO.id", target = "jobs", qualifiedByName = "mapJobs")
    @Mapping(source = "employeeDO", target = "roles", qualifiedByName = "mapRoles")
    @Mapping(source = "employeeDO", target = "leaveFiles", qualifiedByName = "mapLeaveFiles")
    @Mapping(source = "employeeDO.departmentId", target = "departmentName", qualifiedByName = "mapDepartmentName")
//    @Mapping(source = "employeeDO.userId", target = "username", qualifiedByName = "mapUsername")
    @Mapping(source = "employeeDO.userId", target = "userPhone", qualifiedByName = "mapUserPhone")
    @Mapping(source = "employeeDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "employeeDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract EmployeeDetailDTO toDetailDTO(
            EmployeeDO employeeDO,
            EmployeeInfoDO employeeInfoDO,
            @Context List<OrganizationBasicDTO> organizationDTOS,
            @Context List<DepartmentBasicDTO> departmentBasicDTOS,
            @Context List<UserBasicDTO> userBasicDTOS,
            @Context Identity identity
    );

    @Mapping(source = "employeeDO.id", target = "jobs", qualifiedByName = "mapJobs")
    @Mapping(source = "employeeDO.departmentId", target = "departmentName", qualifiedByName = "mapDepartmentName")
    public abstract EmployeeBasicDTO toBasicDTO(EmployeeDO employeeDO, @Context List<DepartmentBasicDTO> departmentBasicDTOS);

    public abstract List<EmployeeBasicDTO> toBasicDTOs(List<EmployeeDO> employeeDOs, @Context List<DepartmentBasicDTO> departmentBasicDTOS);

    @Named("mapJobs")
    public List<JobBasicDTO> mapJobs(Long id) {
        return jobQueryService.getEmployeeJobs(id);
    }

    @Named("mapRoles")
    public List<RoleBasicDTO> mapRoles(EmployeeDO employeeDO) {
        return roleManageClient.getRolesByUser(employeeDO.getOrganizationId(), employeeDO.getUserId(), Long.valueOf(arkSystemId));
    }

    @Named("mapIsHavePermision")
    public String mapIsHavePermision(Integer isHavePermision) {
        return isHavePermision == 0 ? "否" : "是";
    }

    @Named("mapLeaveFiles")
    public List<AttachmentBasicDTO> mapLeaveFiles(EmployeeDO employeeDO) {
        return attachmentClient.getRelatedBasicAttachments("employee", employeeDO.getId(), "leave_files");
    }

    @Named("mapDepartmentName")
    public String mapDepartmentName(Long departmentId, @Context List<DepartmentBasicDTO> departmentBasicDTOS) {
        if (departmentBasicDTOS == null) {
            return "";
        }
        return departmentBasicDTOS.stream().filter(d -> d.getId().equals(departmentId)).findFirst().map(DepartmentBasicDTO::getName).orElse("");
    }

    @Named("mapOrganizationName")
    public String mapOrganizationName(Long organizationId, @Context List<OrganizationBasicDTO> organizationDTOS) {
        if (organizationDTOS == null) {
            return "";
        }
        return organizationDTOS.stream().filter(p -> p.getId().equals(organizationId)).findFirst().map(
                o -> StringUtils.isNotBlank(o.getShortName()) ? o.getShortName() : o.getName()
        ).orElse("");
    }

//    @Named("mapUsername")
//    public String mapUsername(Long userId, @Context List<UserBasicDTO> userBasicDTOS) {
//        if (userBasicDTOS == null) { return ""; }
//        return userBasicDTOS.stream().filter(d -> d.getId().equals(userId)).findFirst().map(UserBasicDTO::getUsername).orElse("");
//    }

    @Named("mapUserPhone")
    public String mapUserPhone(Long userId, @Context List<UserBasicDTO> userBasicDTOS) {
        if (userBasicDTOS == null) {
            return "";
        }
        return userBasicDTOS.stream().filter(d -> d.getId().equals(userId)).findFirst().map(UserBasicDTO::getPhone).orElse("");
    }

    @Named("mapActions")
    public List<Action> mapActions(EmployeeDO employeeDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) {
            return actions;
        }
        //是否是组织创始人
        boolean isCreator = employeeDO.getIdentityTypeId().intValue() == EmployeeIdentityType.CREATOR.getId();
        boolean isLeaveJob = employeeDO.getJobStatusId().intValue() == JobStatus.LEAVE_JOB.getId();
        boolean isOnJob = employeeDO.getJobStatusId().intValue() == JobStatus.ON_JOB.getId();
        if (employeePermission.hasPermission(identity, EmployeePermission.VIEW, employeeDO.getId())) {
            actions.add(EmployeePermission.VIEW.toAction());
        }
        if (employeeDO.getUserId() - identity.getId() != 0 && isLeaveJob && employeePermission.hasPermission(identity, EmployeePermission.ON_JOB, employeeDO.getId())) {
            actions.add(EmployeePermission.ON_JOB.toAction());
        }
        if (employeeDO.getUserId() - identity.getId() != 0 && !isCreator && isOnJob && employeePermission.hasPermission(identity, EmployeePermission.LEAVE_JOB, employeeDO.getId())) {
            actions.add(EmployeePermission.LEAVE_JOB.toAction());
        }
        if (employeeDO.getUserId() - identity.getId() != 0 && !isCreator && employeePermission.hasPermission(identity, EmployeePermission.SET_IDENTITY, employeeDO.getId())) {
            actions.add(EmployeePermission.SET_IDENTITY.toAction());
        }
        if (employeeDO.getUserId().intValue() != 0) {
            if (employeePermission.hasPermission(identity, EmployeePermission.CHANGE_USERNAME, employeeDO.getId())) {
                actions.add(EmployeePermission.CHANGE_USERNAME.toAction());
            }
            if (employeePermission.hasPermission(identity, EmployeePermission.CHANGE_USER_PHONE, employeeDO.getId())) {
                actions.add(EmployeePermission.CHANGE_USER_PHONE.toAction());
            }
        }
        if (employeePermission.hasPermission(identity, EmployeePermission.RESET_PASSWORD, employeeDO.getId())) {
            actions.add(EmployeePermission.RESET_PASSWORD.toAction());
        }
        if (employeePermission.hasPermission(identity, EmployeePermission.UPDATE, employeeDO.getId())) {
            actions.add(EmployeePermission.UPDATE.toAction());
        }
        if (employeeDO.getUserId() - identity.getId() != 0 && !isCreator && employeePermission.hasPermission(identity, EmployeePermission.DELETE, employeeDO.getId())) {
            actions.add(EmployeePermission.DELETE.toAction());
        }
        return actions;
    }
}