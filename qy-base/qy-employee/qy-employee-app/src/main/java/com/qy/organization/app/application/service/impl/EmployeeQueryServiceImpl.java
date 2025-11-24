package com.qy.organization.app.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.identity.api.client.UserClient;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.assembler.EmployeeAssembler;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.enums.CheckedType;
import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.organization.app.application.security.EmployeePermission;
import com.qy.organization.app.application.service.DepartmentQueryService;
import com.qy.organization.app.application.service.EmployeeQueryService;
import com.qy.organization.app.infrastructure.persistence.EmployeeDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeMapper;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.dto.MenuDTO;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationPermissionRuleUtils;
import com.qy.uims.security.permission.OrganizationPermissionScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工查询服务实现
 *
 * @author legendjw
 */
@Service
public class EmployeeQueryServiceImpl implements EmployeeQueryService {
    private EmployeeAssembler employeeAssembler;
    private EmployeeDataRepository employeeDataRepository;
    private DepartmentQueryService departmentQueryService;
    private EmployeePermission employeePermission;
    private OrganizationClient organizationClient;
    private UserClient userClient;
    private AuthClient authClient;
    private MenuClient menuClient;
    private OrganizationPermissionRuleUtils organizationPermissionRuleUtils;
    private OrgDatasourceClient orgDatasourceClient;

    public EmployeeQueryServiceImpl(EmployeeAssembler employeeAssembler, EmployeeDataRepository employeeDataRepository, @Lazy DepartmentQueryService departmentQueryService,
                                    EmployeePermission employeePermission, OrganizationClient organizationClient, UserClient userClient, AuthClient authClient,
                                    MenuClient menuClient, OrganizationPermissionRuleUtils organizationPermissionRuleUtils,
                                    OrgDatasourceClient orgDatasourceClient) {
        this.employeeAssembler = employeeAssembler;
        this.employeeDataRepository = employeeDataRepository;
        this.departmentQueryService = departmentQueryService;
        this.employeePermission = employeePermission;
        this.organizationClient = organizationClient;
        this.userClient = userClient;
        this.authClient = authClient;
        this.menuClient = menuClient;
        this.organizationPermissionRuleUtils = organizationPermissionRuleUtils;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    public Page<EmployeeDTO> getEmployees(EmployeeQuery query, Identity identity) {
//        MultiOrganizationFilterQuery filterQuery = employeePermission.getFilterQuery(identity, StringUtils.isNotBlank(query.getPermission()) ? query.getPermission() : EmployeePermission.LIST.getPermission());
        DepartmentBasicDTO departmentDTO = new DepartmentBasicDTO();
        if (query.getDepartmentId() != null) {
            departmentDTO = departmentQueryService.getBasicDepartmentById(query.getDepartmentId());
            //如果部门是组织的顶级部门，则查找组织下所有员工
            if (departmentDTO.getParentId().longValue() == 0L) {
                query.setOrganizationId(departmentDTO.getOrganizationId());
                query.setDepartmentId(null);
            }
            //如果是普通部门则查找包含所有子部门的所有员工
            else {
                List<DepartmentBasicDTO> departmentDTOS = departmentQueryService.getAllChildDepartments(query.getDepartmentId());
                query.setDepartmentIds(departmentDTOS.stream().map(DepartmentBasicDTO::getId).collect(Collectors.toList()));
            }
        }
        MultiOrganizationFilterQuery filterQuery = new MultiOrganizationFilterQuery();
        if (query.getDepartmentId() != null) {
            filterQuery = getFilterByPermissionScope(identity, departmentDTO, query);
        } else {
            filterQuery = employeePermission.getFilterQuery(identity, StringUtils.isNotBlank(query.getPermission()) ? query.getPermission() : EmployeePermission.LIST.getPermission());
        }
        Page<EmployeeDO> employeeDOPage = employeeDataRepository.findByQuery(query, filterQuery);
        List<EmployeeDO> employeeDOS = employeeDOPage.getRecords();
        List<EmployeeInfoDO> employeeInfoDOS = employeeDOS.isEmpty() ? new ArrayList<>() : employeeDataRepository.findInfoByIds(employeeDOS.stream().map(EmployeeDO::getId).collect(Collectors.toList()));
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(employeeDOS.stream().map(EmployeeDO::getDepartmentId).collect(Collectors.toList()));
        List<OrganizationBasicDTO> organizationBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : organizationClient.getBasicOrganizationsByIds(employeeDOS.stream().map(EmployeeDO::getOrganizationId).collect(Collectors.toList()));
        List<UserBasicDTO> userBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : userClient.getBasicUsersByIds(employeeDOS.stream().map(EmployeeDO::getUserId).collect(Collectors.toList()));
        Page<EmployeeDTO> employeeDTOPage = employeeDOPage.map(employee ->
                employeeAssembler.toDTO(
                        employee,
                        employeeInfoDOS.stream().filter(infoDO -> infoDO.getEmployeeId().equals(employee.getId())).findFirst().orElse(null),
                        organizationBasicDTOS,
                        departmentBasicDTOS,
                        userBasicDTOS,
                        identity
                ));
        return employeeDTOPage;
    }

    private MultiOrganizationFilterQuery getFilterByPermissionScope (Identity identity, DepartmentBasicDTO departmentDTO, EmployeeQuery query) {
        EmployeeBasicDTO employeeBasicDTO = getUserJoinOrganizationBasicEmployee(departmentDTO.getOrganizationId(), identity.getId());

        MultiOrganizationFilterQuery filterQuery = new MultiOrganizationFilterQuery();
        List<Long> organizationIds = new ArrayList<>();
        organizationIds.add(query.getOrganizationId());
        filterQuery.setOrganizationIds(organizationIds);

        List<OrganizationPermissionScope> organizationPermissionScopes = organizationPermissionRuleUtils.getOrganizationPermissionScope(identity, EmployeePermission.LIST.getPermission());
        List<OrganizationPermissionScope> organizationPermissionScope = organizationPermissionScopes.stream().filter(o -> o.getOrganizationId().equals(query.getOrganizationId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(organizationPermissionScope)) {
            OrganizationPermissionScope scope = organizationPermissionScope.get(0);
            if (scope.getPermissionScope().getId().equals("allScope")) { //全部
                List<OrganizationFilterQuery> permissionFilterQueries = new ArrayList<>();
                OrganizationFilterQuery organizationFilterQuery = new OrganizationFilterQuery();
                organizationFilterQuery.setOrganizationId(query.getOrganizationId());
                if (departmentDTO.getParentId().longValue() == 0L) {//查询全部
                    organizationFilterQuery.setDepartmentIds(null);
                } else {
                    organizationFilterQuery.setDepartmentIds(query.getDepartmentIds());
                }

                organizationFilterQuery.setDepartmentId(null);
                organizationFilterQuery.setEmployeeId(null);
                permissionFilterQueries.add(organizationFilterQuery);
                filterQuery.setPermissionFilterQueries(permissionFilterQueries);

            } else if (scope.getPermissionScope().getId().equals("departmentScope")) {//本部门

                List<OrganizationFilterQuery> permissionFilterQueries = new ArrayList<>();
                OrganizationFilterQuery organizationFilterQuery = new OrganizationFilterQuery();
                organizationFilterQuery.setOrganizationId(query.getOrganizationId());

                if (query.getDepartmentId().equals(employeeBasicDTO.getDepartmentId()) || departmentDTO.getParentId().longValue() == 0L) {
                    organizationFilterQuery.setDepartmentId(employeeBasicDTO.getDepartmentId());
                } else if (departmentDTO.getParentId().longValue() != 0L && query.getDepartmentIds().contains(employeeBasicDTO.getDepartmentId())) {
                    organizationFilterQuery.setDepartmentId(employeeBasicDTO.getDepartmentId());
                } else {
                    organizationFilterQuery.setDepartmentId(0L);
                }

                organizationFilterQuery.setDepartmentIds(null);
                organizationFilterQuery.setEmployeeId(null);
                permissionFilterQueries.add(organizationFilterQuery);
                filterQuery.setPermissionFilterQueries(permissionFilterQueries);

            } else if (scope.getPermissionScope().getId().equals("specifiedDepartmentScope")) { //指定部门
                List<Long> departmentIds = JSON.parseArray(organizationPermissionScope.get(0).getPermissionScopeData().toString(), Long.class);
                List<Long> shareDepartmenIds = new ArrayList<>();
                if (departmentDTO.getParentId().longValue() == 0L) {//查询全部
//                    if (!departmentIds.contains(query.getDepartmentId())) {
//                        departmentIds.add(query.getDepartmentId());
//                    }
                    shareDepartmenIds = departmentIds;
                } else {
                    //取交集
                    if (!CollectionUtils.isEmpty(query.getDepartmentIds())) {
                        shareDepartmenIds = query.getDepartmentIds().stream().filter(o -> departmentIds.contains(o)).collect(Collectors.toList());
                    }
                    if (!CollectionUtils.isEmpty(shareDepartmenIds)) {
                        if (!shareDepartmenIds.contains(query.getDepartmentId())) {
                            shareDepartmenIds.add(query.getDepartmentId());
                        }
                    } else {
                        shareDepartmenIds.add(0L);
                    }
                }
                List<OrganizationFilterQuery> permissionFilterQueries = new ArrayList<>();
                OrganizationFilterQuery organizationFilterQuery = new OrganizationFilterQuery();
                organizationFilterQuery.setOrganizationId(query.getOrganizationId());
                organizationFilterQuery.setDepartmentId(null);
                organizationFilterQuery.setDepartmentIds(shareDepartmenIds);
                organizationFilterQuery.setEmployeeId(null);
                permissionFilterQueries.add(organizationFilterQuery);
                filterQuery.setPermissionFilterQueries(permissionFilterQueries);

            } else if (scope.getPermissionScope().getId().equals("selfScope")) { //自己的
                List<OrganizationFilterQuery> permissionFilterQueries = new ArrayList<>();
                OrganizationFilterQuery organizationFilterQuery = new OrganizationFilterQuery();
                organizationFilterQuery.setOrganizationId(query.getOrganizationId());
                organizationFilterQuery.setDepartmentId(null);
                organizationFilterQuery.setDepartmentIds(null);
                organizationFilterQuery.setEmployeeId(identity.getId());
                permissionFilterQueries.add(organizationFilterQuery);
                filterQuery.setPermissionFilterQueries(permissionFilterQueries);
            }
        }

        return filterQuery;
    }

    @Override
    public Page<EmployeeBasicDTO> getBasicEmployees(EmployeeQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = employeePermission.getFilterQuery(identity, StringUtils.isNotBlank(query.getPermission()) ? query.getPermission() : EmployeePermission.LIST.getPermission());
        if (query.getDepartmentId() != null) {
            DepartmentBasicDTO departmentDTO = departmentQueryService.getBasicDepartmentById(query.getDepartmentId());
            //如果部门是组织的顶级部门，则查找组织下所有员工
            if (departmentDTO.getParentId().longValue() == 0L) {
                query.setOrganizationId(departmentDTO.getOrganizationId());
            }
            //如果是普通部门则查找包含所有子部门的所有员工
            else {
                List<DepartmentBasicDTO> departmentDTOS = departmentQueryService.getAllChildDepartments(query.getDepartmentId());
                query.setDepartmentIds(departmentDTOS.stream().map(DepartmentBasicDTO::getId).collect(Collectors.toList()));
            }
        }

        Page<EmployeeDO> employeeDOPage = employeeDataRepository.findByQuery(query, filterQuery);
        List<EmployeeDO> employeeDOS = employeeDOPage.getRecords();
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(employeeDOS.stream().map(EmployeeDO::getDepartmentId).collect(Collectors.toList()));
        Page<EmployeeBasicDTO> employeeDTOPage = employeeDOPage.map(employee ->
                employeeAssembler.toBasicDTO(employee, departmentBasicDTOS)
        );
        return employeeDTOPage;
    }

    @Override
    public List<EmployeeBasicDTO> getBasicEmployees(EmployeeQuery query) {
        List<EmployeeDO> employeeDOS = employeeDataRepository.findByQuery(query);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(employeeDOS.stream().map(EmployeeDO::getDepartmentId).collect(Collectors.toList()));
        return employeeDOS.stream().map(employeeDO -> employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS)).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeBasicDTO> getBasicEmployees(List<Long> ids) {
        List<EmployeeDO> employeeDOS = employeeDataRepository.findByIds(ids);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(employeeDOS.stream().map(EmployeeDO::getDepartmentId).collect(Collectors.toList()));
        return employeeDOS.stream().map(employeeDO -> employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS)).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeBasicDTO> getHasPermissionUsers(Long organizationId, String permission) {
        List<String> userIds = authClient.getHasPermissionUsers(OrganizationSessionContext.contextId, organizationId.toString(), permission);
        if (userIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<EmployeeDO> employeeDOS = employeeDataRepository.findByUserIds(userIds.stream().map(Long::new).collect(Collectors.toList()));
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDOS.isEmpty() ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(employeeDOS.stream().map(EmployeeDO::getDepartmentId).collect(Collectors.toList()));
        return employeeDOS.stream().map(employeeDO -> employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS)).collect(Collectors.toList());
    }

    @Override
    public EmployeeBasicDTO getUserJoinOrganizationBasicEmployee(Long organizationId, Long userId) {
        EmployeeDO employeeDO = employeeDataRepository.findByOrganizationIdAndUserId(organizationId, userId);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService
                .getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        return employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS);
    }

    @Override
    public List<EmployeeBasicDTO> getUserJoinOrganizationBasicEmployees(Long userId) {
        List<EmployeeDO> employeeDOS = employeeDataRepository.findByUserId(userId);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDOS.isEmpty() ?
                new ArrayList<>() : departmentQueryService.
                getBasicDepartmentsByIds(employeeDOS.stream().map(EmployeeDO::getDepartmentId).collect(Collectors.toList()));
        return employeeAssembler.toBasicDTOs(employeeDOS, departmentBasicDTOS);
    }

    @Override
    public EmployeeBasicDTO getBasicEmployeeById(Long id) {
        EmployeeDO employeeDO = employeeDataRepository.findById(id);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        return employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS);
    }

    @Override
    public EmployeeBasicDTO getBasicEmployeeByOrganizationId(Long id, Long organizationId) {
        //切换数据库
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(organizationId);
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        EmployeeDO employeeDO = employeeDataRepository.findById(id);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        //清除数据库
        DynamicDataSourceContextHolder.clear();
        return employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS);
    }

    @Override
    public EmployeeBasicDTO getBasicEmployeeByMemberId(Long memberId) {
        EmployeeDO employeeDO = employeeDataRepository.findByMemberId(memberId);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        return employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS);
    }

    @Override
    public EmployeeBasicDTO getBasicEmployeeByPhone(String phone) {
        EmployeeDO employeeDO = employeeDataRepository.findByPhone(phone);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        return employeeAssembler.toBasicDTO(employeeDO, departmentBasicDTOS);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id, Identity identity) {
        EmployeeDO employeeDO = employeeDataRepository.findById(id);
        EmployeeInfoDO employeeInfoDO = employeeDataRepository.findInfoById(id);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        List<OrganizationBasicDTO> organizationBasicDTOS = employeeDO == null ? new ArrayList<>() : organizationClient.getBasicOrganizationsByIds(Arrays.asList(employeeDO.getOrganizationId()));
        List<UserBasicDTO> userBasicDTOS = employeeDO == null ? new ArrayList<>() : userClient.getBasicUsersByIds(Arrays.asList(employeeDO.getUserId()));
        return employeeAssembler.toDTO(employeeDO, employeeInfoDO, organizationBasicDTOS, departmentBasicDTOS, userBasicDTOS, identity);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeDO employeeDO = employeeDataRepository.findById(id);
        EmployeeInfoDO employeeInfoDO = employeeDataRepository.findInfoById(id);
        List<OrganizationBasicDTO> organizationBasicDTOS = employeeDO == null ? new ArrayList<>() : organizationClient.getBasicOrganizationsByIds(Arrays.asList(employeeDO.getOrganizationId()));
        return employeeAssembler.toDTO(employeeDO, employeeInfoDO, organizationBasicDTOS, null, null, null);
    }

    @Override
    public EmployeeDetailDTO getEmployeeDetailById(Long id, Identity identity) {
        EmployeeDO employeeDO = employeeDataRepository.findById(id);
        EmployeeInfoDO employeeInfoDO = employeeDataRepository.findInfoById(id);
        List<DepartmentBasicDTO> departmentBasicDTOS = employeeDO == null ? new ArrayList<>() : departmentQueryService.getBasicDepartmentsByIds(Arrays.asList(employeeDO.getDepartmentId()));
        List<OrganizationBasicDTO> organizationBasicDTOS = employeeDO == null ? new ArrayList<>() : organizationClient.getBasicOrganizationsByIds(Arrays.asList(employeeDO.getOrganizationId()));
        List<UserBasicDTO> userBasicDTOS = employeeDO == null ? new ArrayList<>() : userClient.getBasicUsersByIds(Arrays.asList(employeeDO.getUserId()));
        return employeeAssembler.toDetailDTO(employeeDO, employeeInfoDO, organizationBasicDTOS, departmentBasicDTOS, userBasicDTOS, identity);
    }

    @Override
    public EmployeeBasicDTO getOrganizationCreator(Long organizationId) {
        //切换数据库
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(organizationId);
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        EmployeeDO employeeDO = employeeDataRepository.findOrganizationCreator(organizationId);
        //清除数据库
        DynamicDataSourceContextHolder.clear();
        return employeeAssembler.toBasicDTO(employeeDO, null);
    }

    @Override
    public RoleMenuPermissionDTO getEmployeeMenuPermission(Long employeeId, Identity identity) {
        EmployeeDO employeeDO = employeeDataRepository.findById(employeeId);
        if (employeeDO == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        RoleMenuPermissionDTO roleMenuPermissionDTO = new RoleMenuPermissionDTO();
        //获取权限组下权限
        List<PermissionWithRuleDTO> permissionWithRuleDTOS = authClient.getUserPermissions(employeeDO.getUserId().toString(), OrganizationSessionContext.contextId, employeeDO.getOrganizationId().toString());
        List<String> permissions = permissionWithRuleDTOS.stream().map(PermissionWithRuleDTO::getPermission).collect(Collectors.toList());
        List<MenuDTO> menuDTOS = menuClient.getPermissionParentMenus(identity.getId().toString(), OrganizationSessionContext.contextId, employeeDO.getOrganizationId().toString());

        //计算权限菜单勾选信息
        List<MenuCheckDTO> menuCheckDTOS = new ArrayList<>();
        for (MenuDTO menuDTO : menuDTOS) {
            MenuCheckDTO menuCheckDTO = new MenuCheckDTO();
            menuCheckDTO.setId(menuDTO.getId());
            menuCheckDTO.setName(menuDTO.getName());
            if (menuDTO.getChildren() == null || menuDTO.getChildren().isEmpty()) {
                menuCheckDTO.setChecked(CheckedType.NONE.getId());
            }
            else if (menuDTO.getChildren().stream().allMatch(c -> permissions.contains(c.getAuthItem()))) {
                menuCheckDTO.setChecked(CheckedType.FULL.getId());
            }
            else if (menuDTO.getChildren().stream().anyMatch(c -> permissions.contains(c.getAuthItem()))) {
                menuCheckDTO.setChecked(CheckedType.HALF.getId());
            }
            else {
                menuCheckDTO.setChecked(CheckedType.NONE.getId());
            }

            menuCheckDTOS.add(menuCheckDTO);
        }

        roleMenuPermissionDTO.setPermissions(permissionWithRuleDTOS);
        roleMenuPermissionDTO.setMenuChecks(menuCheckDTOS);

        return roleMenuPermissionDTO;
    }

    @Override
    public SendMessageEmployeeInfoDTO getSendMessageEmployeeInfo(Long employeeId) {
        EmployeeDO employeeDO = employeeDataRepository.findById(employeeId);
        if (employeeDO == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        OrganizationBasicDTO organizationBasicDTO = organizationClient.getBasicOrganizationById(employeeDO.getOrganizationId());
        SendMessageEmployeeInfoDTO sendMessageEmployeeInfoDTO = new SendMessageEmployeeInfoDTO();
        sendMessageEmployeeInfoDTO.setEmployeeId(employeeDO.getId());
        sendMessageEmployeeInfoDTO.setUserId(employeeDO.getUserId());
        sendMessageEmployeeInfoDTO.setOrganizationId(organizationBasicDTO.getId());
        sendMessageEmployeeInfoDTO.setOrganizationName(StringUtils.isNotBlank(organizationBasicDTO.getShortName()) ? organizationBasicDTO.getShortName() : organizationBasicDTO.getName());
        sendMessageEmployeeInfoDTO.setPhone(employeeDO.getPhone());
        sendMessageEmployeeInfoDTO.setEmail(employeeDO.getEmail());
        sendMessageEmployeeInfoDTO.setMobileId("");
        sendMessageEmployeeInfoDTO.setOpenId("");
        return sendMessageEmployeeInfoDTO;
    }
}
