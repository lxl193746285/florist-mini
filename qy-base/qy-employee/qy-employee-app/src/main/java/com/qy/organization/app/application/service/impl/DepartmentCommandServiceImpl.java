package com.qy.organization.app.application.service.impl;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

import com.alibaba.fastjson.JSON;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.command.CreateDepartmentCommand;
import com.qy.organization.app.application.command.DeleteDepartmentCommand;
import com.qy.organization.app.application.command.UpdateDepartmentCommand;
import com.qy.organization.app.application.dto.DepartmentDTO;
import com.qy.organization.app.application.dto.RbacAuthItemChildDTO;
import com.qy.organization.app.application.dto.RbacAuthItemChildDetailDTO;
import com.qy.organization.app.application.service.DepartmentCommandService;
import com.qy.organization.app.infrastructure.persistence.DepartmentDataRepository;
import com.qy.organization.app.infrastructure.persistence.EmployeeDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.DepartmentMapper;
import com.qy.rbac.api.client.RoleClient;
import com.qy.rbac.api.command.RbacAuthorizeRoleCommand;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.util.PinyinUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门命令实现
 *
 * @author legendjw
 */
@Service
public class DepartmentCommandServiceImpl implements DepartmentCommandService {
    @Autowired
    private DepartmentMapper departmentMapper;
//    @Autowired
//    private RoleClient roleClient;
    private DepartmentDataRepository departmentDataRepository;
    private OrganizationClient organizationClient;
    private EmployeeDataRepository employeeDataRepository;
    private OrgDatasourceClient orgDatasourceClient;

    public DepartmentCommandServiceImpl(DepartmentDataRepository departmentDataRepository, OrganizationClient organizationClient, EmployeeDataRepository employeeDataRepository, OrgDatasourceClient orgDatasourceClient) {
        this.departmentDataRepository = departmentDataRepository;
        this.organizationClient = organizationClient;
        this.employeeDataRepository = employeeDataRepository;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    public Long createOrganizationTopDepartment(Long organizationId) {
        //切换数据库
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(organizationId);
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        OrganizationBasicDTO organization = organizationClient.getBasicOrganizationById(organizationId);
        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setOrganizationId(organizationId);
        departmentDO.setName(StringUtils.isNotBlank(organization.getShortName()) ? organization.getShortName() : organization.getName());
        departmentDO.setNamePinyin(PinyinUtils.getAlpha(departmentDO.getName()));
        departmentDO.setStatusId(EnableDisableStatus.ENABLE.getId());
        departmentDO.setStatusName(EnableDisableStatus.ENABLE.getName());
        departmentDO.setCreateTime(LocalDateTime.now());
        departmentDataRepository.save(departmentDO);
        //清除数据库
        DynamicDataSourceContextHolder.clear();
        return departmentDO.getId();
    }

    @Override
    public Long createDepartment(CreateDepartmentCommand command) {
        if (departmentDataRepository.countByOrganizationIdAndName(command.getOrganizationId(), command.getName(), null) > 0) {
            throw new ValidationException("组织下已经存在同名的部门，请更换新的部门名称");
        }
        EmployeeIdentity employee = command.getEmployee();

        EmployeeDO leader = command.getLeaderId() != null && command.getLeaderId().longValue() != 0L ? employeeDataRepository.findById(command.getLeaderId()) : null;
        DepartmentDO departmentDO = new DepartmentDO();
        BeanUtils.copyProperties(command, departmentDO, "employee");
        departmentDO.setNamePinyin(PinyinUtils.getAlpha(departmentDO.getName()));
        departmentDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        departmentDO.setLeaderName(leader != null ? leader.getName() : "");
        departmentDO.setCreatorId(employee.getId());
        departmentDO.setCreatorName(employee.getName());
        departmentDO.setCreateTime(LocalDateTime.now());
        Long departmentId = departmentDataRepository.save(departmentDO);

        return departmentDO.getId();
    }

    @Override
    public void updateDepartment(UpdateDepartmentCommand command) {
        DepartmentDO departmentDO = departmentDataRepository.findById(command.getId());
        if (departmentDO == null) {
            throw new NotFoundException("未找到指定的部门");
        }
        if (departmentDataRepository.countByOrganizationIdAndName(departmentDO.getOrganizationId(), command.getName(), departmentDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的部门，请更换新的部门名称");
        }
        if (command.getParentId().equals(departmentDO.getId())) {
            throw new NotFoundException("父级部门不能选择自己");
        }
        EmployeeIdentity employee = command.getEmployee();

        EmployeeDO leader = command.getLeaderId() != null && command.getLeaderId().longValue() != 0L ? employeeDataRepository.findById(command.getLeaderId()) : null;
        BeanUtils.copyProperties(command, departmentDO, "employee");
        departmentDO.setNamePinyin(PinyinUtils.getAlpha(departmentDO.getName()));
        departmentDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        if (command.getLeaderId() == null) {
            departmentDO.setLeaderId(0L);
            departmentDO.setLeaderName("");
        } else {
            departmentDO.setLeaderId(command.getLeaderId());
            departmentDO.setLeaderName(leader != null ? leader.getName() : "");
        }
        departmentDO.setUpdatorId(employee.getId());
        departmentDO.setUpdatorName(employee.getName());
        departmentDO.setUpdateTime(LocalDateTime.now());
        departmentDataRepository.save(departmentDO);
    }

    @Override
    public void deleteDepartment(DeleteDepartmentCommand command) {
        if (departmentDataRepository.countByParentId(command.getId()) > 0) {
            throw new ValidationException("部门下含有子部门无法删除");
        }
        EmployeeIdentity employee = command.getEmployee();
        departmentDataRepository.remove(command.getId(), employee);
    }
}