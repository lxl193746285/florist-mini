package com.qy.organization.app.application.service.impl;

import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.assembler.DepartmentAssembler;
import com.qy.organization.app.application.dto.DepartmentBasicDTO;
import com.qy.organization.app.application.dto.DepartmentChildInfoDTO;
import com.qy.organization.app.application.dto.DepartmentDTO;
import com.qy.organization.app.application.query.DepartmentQuery;
import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.organization.app.application.security.DepartmentPermission;
import com.qy.organization.app.application.service.DepartmentQueryService;
import com.qy.organization.app.application.service.EmployeeQueryService;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.infrastructure.persistence.DepartmentDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门查询服务实现
 *
 * @author legendjw
 */
@Service
public class DepartmentQueryServiceImpl implements DepartmentQueryService {
    private DepartmentDataRepository departmentDataRepository;
    private DepartmentAssembler departmentAssembler;
    private DepartmentPermission departmentPermission;
    private OrganizationClient organizationClient;
    private EmployeeQueryService employeeQueryService;

    public DepartmentQueryServiceImpl(DepartmentDataRepository departmentDataRepository, DepartmentAssembler departmentAssembler,
                                      DepartmentPermission departmentPermission, OrganizationClient organizationClient,
                                      @Lazy EmployeeQueryService employeeQueryService) {
        this.departmentDataRepository = departmentDataRepository;
        this.departmentAssembler = departmentAssembler;
        this.departmentPermission = departmentPermission;
        this.organizationClient = organizationClient;
        this.employeeQueryService = employeeQueryService;
    }

    @Override
    public List<DepartmentDTO> getDepartments(DepartmentQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = departmentPermission.getFilterQuery(identity, DepartmentPermission.LIST);
        List<DepartmentDO> departmentDOS = departmentDataRepository.findByQuery(query, filterQuery);
        if (departmentDOS.isEmpty()) { return new ArrayList<>(); }
        List<DepartmentDO> parentDepartments = departmentDataRepository.findByIds(departmentDOS.stream().map(DepartmentDO::getParentId).collect(Collectors.toList()));
        List<OrganizationBasicDTO> organizationBasicDTOS = organizationClient.getBasicOrganizationsByIds(departmentDOS.stream().map(DepartmentDO::getOrganizationId).collect(Collectors.toList()));
        return departmentAssembler.toDTOs(departmentDOS, parentDepartments, organizationBasicDTOS, identity);
    }

    @Override
    public List<DepartmentDTO> getDepartmentsForShV2(MultiOrganizationFilterQuery filterQuery,DepartmentQuery query, Identity identity) {
//        MultiOrganizationFilterQuery filterQuery = departmentPermission.getFilterQuery(identity, DepartmentPermission.LIST);
        List<DepartmentDO> departmentDOS = departmentDataRepository.findByQuery(query, filterQuery);
        if (departmentDOS.isEmpty()) { return new ArrayList<>(); }
        List<DepartmentDO> parentDepartments = departmentDataRepository.findByIds(departmentDOS.stream().map(DepartmentDO::getParentId).collect(Collectors.toList()));
        List<OrganizationBasicDTO> organizationBasicDTOS = organizationClient.getBasicOrganizationsByIds(departmentDOS.stream().map(DepartmentDO::getOrganizationId).collect(Collectors.toList()));
        return departmentAssembler.toDTOs(departmentDOS, parentDepartments, organizationBasicDTOS, identity);
    }

    @Override
    public List<DepartmentBasicDTO> getBasicDepartments(DepartmentQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = departmentPermission.getFilterQuery(identity, StringUtils.isNotBlank(query.getPermission()) ? query.getPermission() : DepartmentPermission.LIST.getPermission());
        List<DepartmentDO> departmentDOS = departmentDataRepository.findByQuery(query, filterQuery);
        return departmentAssembler.toBasicDTOs(departmentDOS);
    }

    @Override
    public List<DepartmentBasicDTO> getBasicDepartments(DepartmentQuery query) {
        List<DepartmentDO> departmentDOS = departmentDataRepository.findByQuery(query);
        return departmentAssembler.toBasicDTOs(departmentDOS);
    }

    @Override
    public DepartmentChildInfoDTO getDepartmentChildInfo(Long organizationId, Long departmentId, String keywords) {
        if (departmentId == null || departmentId.longValue() == 0L) {
            DepartmentDO topDepartment = departmentDataRepository.findOrganizationTopDepartment(organizationId);
            departmentId = topDepartment.getId();
        }
        DepartmentDO departmentDO = departmentDataRepository.findById(departmentId);
        if (departmentDO == null) {
            throw new NotFoundException("未找到指定的部门");
        }
        DepartmentChildInfoDTO departmentChildInfoDTO = new DepartmentChildInfoDTO();
        departmentChildInfoDTO.setId(departmentDO.getId());
        departmentChildInfoDTO.setName(departmentDO.getName());
        departmentChildInfoDTO.setParentId(departmentDO.getParentId());
        EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.setOrganizationId(departmentDO.getOrganizationId());
        employeeQuery.setDepartmentId(departmentDO.getId());
        employeeQuery.setKeywords(keywords);
        employeeQuery.setJobStatus(JobStatus.ON_JOB.getId());
        departmentChildInfoDTO.setEmployees(employeeQueryService.getBasicEmployees(employeeQuery));

        List<DepartmentDO> children = departmentDataRepository.findChildren(departmentDO.getId());
        for (DepartmentDO child : children) {
            DepartmentChildInfoDTO childInfoDTO = new DepartmentChildInfoDTO();
            childInfoDTO.setId(child.getId());
            childInfoDTO.setName(child.getName());
            childInfoDTO.setParentId(child.getParentId());
            departmentChildInfoDTO.getChildren().add(childInfoDTO);
        }
        if (StringUtils.isNotBlank(keywords)) {
            departmentChildInfoDTO.setChildren(departmentChildInfoDTO.getChildren().stream().filter(c -> c.getName().contains(keywords)).collect(Collectors.toList()));
        }

        return departmentChildInfoDTO;
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        DepartmentDO departmentDO = departmentDataRepository.findById(id);
        return departmentAssembler.toDTO(departmentDO, null, null, null);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id, Identity identity) {
        DepartmentDO departmentDO = departmentDataRepository.findById(id);
        List<DepartmentDO> parentDepartments = departmentDO.getParentId().longValue() == 0L ? new ArrayList<>() : departmentDataRepository.findByIds(Arrays.asList(departmentDO.getParentId()));
        List<OrganizationBasicDTO> organizationBasicDTOS = organizationClient.getBasicOrganizationsByIds(Arrays.asList(departmentDO.getOrganizationId()));
        return departmentAssembler.toDTO(departmentDO, parentDepartments, organizationBasicDTOS, identity);
    }

    @Override
    public DepartmentBasicDTO getBasicDepartmentById(Long id) {
        DepartmentDO departmentDO = departmentDataRepository.findById(id);
        return departmentAssembler.toBasicDTO(departmentDO);
    }

    @Override
    public List<DepartmentBasicDTO> getBasicDepartmentsByIds(List<Long> ids) {
        List<DepartmentDO> departmentDOs = departmentDataRepository.findByIds(ids);
        return departmentAssembler.toBasicDTOs(departmentDOs);
    }

    @Override
    public List<DepartmentBasicDTO> getAllChildDepartments(Long id) {
        DepartmentDO departmentDO = departmentDataRepository.findById(id);
        if (departmentDO == null) {
            throw new NotFoundException("未找到指定的部门");
        }
        List<DepartmentDO> departmentDOS = new ArrayList<>();
        departmentDOS.add(departmentDO);
        recursionLoadChildDepartments(id, departmentDOS);

        return departmentAssembler.toBasicDTOs(departmentDOS);
    }
    /**
     * 递归加载子部门
     *
     * @param id
     * @param departmentDOS
     */
    private void recursionLoadChildDepartments(Long id, List<DepartmentDO> departmentDOS) {
        List<DepartmentDO> childDepartments = departmentDataRepository.findChildren(id);
        departmentDOS.addAll(childDepartments);
        for (DepartmentDO childDepartment : childDepartments) {
            recursionLoadChildDepartments(childDepartment.getId(), departmentDOS);
        }
    }
}