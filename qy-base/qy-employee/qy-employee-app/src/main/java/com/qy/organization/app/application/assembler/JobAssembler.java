package com.qy.organization.app.application.assembler;

import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.dto.JobBasicDTO;
import com.qy.organization.app.application.dto.JobDTO;
import com.qy.organization.app.application.security.JobPermission;
import com.qy.organization.app.application.service.EmployeeQueryService;
import com.qy.organization.app.infrastructure.persistence.JobDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
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
 * 岗位汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class JobAssembler {
    @Autowired
    private JobDataRepository jobDataRepository;
    @Autowired
    private EmployeeQueryService employeeQueryService;
    @Autowired
    private JobPermission jobPermission;

    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "jobDO", target = "employees", qualifiedByName = "mapEmployees")
    @Mapping(source = "jobDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "jobDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "jobDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract JobDTO toDTO(JobDO jobDO, @Context List<OrganizationBasicDTO> organizationDTOS, @Context Identity identity);

    public abstract JobBasicDTO toBasicDTO(JobDO jobDO);

    public abstract List<JobBasicDTO> toBasicDTOs(List<JobDO> jobDOs);

    @Named("mapEmployees")
    public List<EmployeeBasicDTO> mapEmployees(JobDO jobDO) {
        List<Long> employeeIds = jobDataRepository.findJobEmployees(jobDO.getId());
        return employeeQueryService.getBasicEmployees(employeeIds);
    }

    @Named("mapActions")
    protected List<Action> mapActions(JobDO jobDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (jobPermission.hasPermission(identity, JobPermission.UPDATE, jobDO.getId())) {
            actions.add(JobPermission.UPDATE.toAction());
        }
        if (jobPermission.hasPermission(identity, JobPermission.DELETE, jobDO.getId())) {
            actions.add(JobPermission.DELETE.toAction());
        }
        return actions;
    }

    @Named("mapOrganizationName")
    public String mapOrganizationName(Long organizationId, @Context List<OrganizationBasicDTO> organizationDTOS) {
        if (organizationDTOS == null) { return ""; }
        return organizationDTOS.stream().filter(p -> p.getId().equals(organizationId)).findFirst().map(
                o -> StringUtils.isNotBlank(o.getShortName()) ? o.getShortName() : o.getName()
        ).orElse("");
    }
}