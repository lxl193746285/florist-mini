package com.qy.organization.app.domain.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.identity.api.client.UserClient;
import com.qy.identity.api.command.CreateAccountCommand;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.identity.api.enums.UserSource;
import com.qy.member.api.client.AccountClient;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.command.CreateMemberCommand;
import com.qy.member.api.dto.AccountBasicDTO;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.member.api.dto.MemberIdDTO;
import com.qy.member.api.query.MemberQuery;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.command.AssignRoleToEmployeeCommand;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.organization.app.application.dto.MenuCheckWithPermissionDTO;
import com.qy.organization.app.application.enums.CheckedType;
import com.qy.organization.app.domain.entity.Department;
import com.qy.organization.app.domain.entity.Employee;
import com.qy.organization.app.domain.entity.EmployeeInfo;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.EmployeePermissionType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.domain.service.EmployeeService;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.infrastructure.persistence.DepartmentDomainRepository;
import com.qy.organization.app.infrastructure.persistence.EmployeeDomainRepository;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.client.RoleClient;
import com.qy.rbac.api.command.RbacCopyUserPermissionCommand;
import com.qy.rbac.api.command.RbacCreateRoleAndAuthorizeCommand;
import com.qy.rbac.api.command.RbacRevokeUserRoleCommand;
import com.qy.rbac.api.dto.MenuDTO;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工领域服务
 *
 * @author legendjw
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Value("${qy.organization.ark-system-id}")
    private String arkSystemId;

    private EmployeeDomainRepository employeeDomainRepository;
    private DepartmentDomainRepository departmentDomainRepository;
    private RoleManageClient roleManageClient;
    private RoleClient roleClient;
    private UserClient userClient;
    private AuthClient authClient;
    private MenuClient menuClient;
    private AccountClient accountClient;
    private OrgDatasourceClient orgDatasourceClient;

    /**
     * 员工账号添加至会员系统，权限变更至会员权限
     * @ wwd
     */
    private MemberClient memberClient;

    public EmployeeServiceImpl(EmployeeDomainRepository employeeDomainRepository, DepartmentDomainRepository departmentDomainRepository, RoleManageClient roleManageClient, RoleClient roleClient,
                               UserClient userClient, AuthClient authClient, MenuClient menuClient, MemberClient memberClient, OrgDatasourceClient orgDatasourceClient,
                               AccountClient accountClient) {
        this.employeeDomainRepository = employeeDomainRepository;
        this.departmentDomainRepository = departmentDomainRepository;
        this.roleManageClient = roleManageClient;
        this.roleClient = roleClient;
        this.userClient = userClient;
        this.authClient = authClient;
        this.menuClient = menuClient;
        this.memberClient = memberClient;
        this.accountClient = accountClient;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    @Transactional
    public void changeToAdmin(EmployeeId employeeId) {
        Employee employee = employeeDomainRepository.findById(employeeId);
        //创建账号
        // if (!employee.hasCreateAccount()) {
        //     UserBasicDTO userBasicDTO = createAccount(employee);
        //     employee.bindAccount(userBasicDTO.getId());
        // }
        // 此处变更为会员系统
        if (!employee.hasCreateAccount()) {
            MemberBasicDTO memberBasicDTO = createMemberAccount(employee);
            employee.bindAccount(memberBasicDTO.getAccountId());
            employee.bindMember(memberBasicDTO.getId());
        }
        employee.modifyIdentityType(EmployeeIdentityType.ADMIN);
        employee.modifyPermissionType(null);
        employeeDomainRepository.save(employee);

        //撤销员工的所有权限组权限
        roleManageClient.revokeEmployeeRole(employee.getId().getId());

        //复制组织创始人权限给管理员
        Employee organizationCreator = employeeDomainRepository.findOrganizationCreator(employee.getOrganizationId());
        RbacCopyUserPermissionCommand copyUserPermissionCommand = new RbacCopyUserPermissionCommand();
        copyUserPermissionCommand.setContext(OrganizationSessionContext.contextId);
        copyUserPermissionCommand.setContextId(employee.getOrganizationId().getId().toString());
        copyUserPermissionCommand.setSourceUserId(organizationCreator.getUserId().getId().toString());
        copyUserPermissionCommand.setToUserId(employee.getUserId().getId().toString());
        roleClient.copyUserPermission(copyUserPermissionCommand);
    }

    @Override
    @Transactional
    public void changeToOperator(EmployeeId employeeId, EmployeePermissionType permissionType, List<Long> roleIds, List<MenuCheckWithPermissionDTO> menuPermissions, Identity identity) {
        Employee employee = employeeDomainRepository.findById(employeeId);
        // //创建账号
        // if (!employee.hasCreateAccount()) {
        //     UserBasicDTO userBasicDTO = createAccount(employee);
        //     employee.bindAccount(userBasicDTO.getId());
        // }
        // 此处变更为会员系统
        if (!employee.hasCreateAccount()) {
            MemberBasicDTO memberBasicDTO = createMemberAccount(employee);
            employee.bindAccount(memberBasicDTO.getAccountId());
            employee.bindMember(memberBasicDTO.getId());
        }
        employee.modifyIdentityType(EmployeeIdentityType.OPERATOR);
        employee.modifyPermissionType(permissionType);
        employeeDomainRepository.save(employee);

        //权限组授权
        if (employee.getPermissionType().equals(EmployeePermissionType.PERMISSION_GROUP)) {
            AssignRoleToEmployeeCommand assignRoleToEmployeeCommand = new AssignRoleToEmployeeCommand();
            assignRoleToEmployeeCommand.setEmployeeId(employee.getId().getId());
            assignRoleToEmployeeCommand.setUserId(employee.getUserId().getId());
            assignRoleToEmployeeCommand.setRoleIds(roleIds);
            roleManageClient.assignRoleToEmployee(assignRoleToEmployeeCommand);
        }
        //独立权限授权
        else if (employee.getPermissionType().equals(EmployeePermissionType.INDEPENDENT)) {
            //撤销员工的所有权限组权限
            roleManageClient.revokeEmployeeRole(employee.getId().getId());

            RbacCreateRoleAndAuthorizeCommand createRoleAndAuthorizeCommand = new RbacCreateRoleAndAuthorizeCommand();
            if (StringUtils.isBlank(employee.getAuthItem())) {
                employee.bindAuthItem(employee.generateAuthItem());
            }
            List<PermissionWithRuleDTO> permissionWithRuleDTOS = getAuthorizePermissions(employee, menuPermissions, identity);
            createRoleAndAuthorizeCommand.setRoleId(employee.getAuthItem());
            createRoleAndAuthorizeCommand.setRoleDescription(employee.getName().getName());
            createRoleAndAuthorizeCommand.setUserId(employee.getUserId().getId().toString());
            createRoleAndAuthorizeCommand.setContext(OrganizationSessionContext.contextId);
            createRoleAndAuthorizeCommand.setContextId(employee.getOrganizationId().getId().toString());
            createRoleAndAuthorizeCommand.setPermissions(permissionWithRuleDTOS);
            createRoleAndAuthorizeCommand.setSystemId(Long.parseLong(arkSystemId));
            roleClient.createRoleAndAuthorize(createRoleAndAuthorizeCommand);
        }
    }

    @Override
    @Transactional
    public void changeToEmployee(EmployeeId employeeId) {
        Employee employee = employeeDomainRepository.findById(employeeId);
        // //创建账号
        // if (!employee.hasCreateAccount()) {
        //     UserBasicDTO userBasicDTO = createAccount(employee);
        //     employee.bindAccount(userBasicDTO.getId());
        // }
        // 此处变更为会员系统
        if (!employee.hasCreateAccount()) {
            MemberBasicDTO memberBasicDTO = createMemberAccount(employee);
            employee.bindAccount(memberBasicDTO.getAccountId());
            employee.bindMember(memberBasicDTO.getId());
        }

        employee.modifyIdentityType(EmployeeIdentityType.EMPLOYEE);
        employee.modifyPermissionType(null);
        employeeDomainRepository.save(employee);

        //撤销员工的所有权限组权限
        roleManageClient.revokeEmployeeRole(employee.getId().getId());
    }

    /**
     * 给员工创建账号
     *
     * @param employee
     * @return
     */
    private UserBasicDTO createAccount(Employee employee) {
        UserBasicDTO userBasicDTO = userClient.getBasicUserByPhone(employee.getPhone().getNumber());
        if (userBasicDTO == null) {
            CreateAccountCommand createAccountCommand = new CreateAccountCommand();
            createAccountCommand.setName(employee.getName().getName());
            createAccountCommand.setPhone(employee.getPhone() != null ? employee.getPhone().getNumber() : null);
            createAccountCommand.setEmail(employee.getEmail() != null ? employee.getEmail().getAddress() : null);
            createAccountCommand.setSource(UserSource.ORGANIZATION.getId());
            userBasicDTO = userClient.createAccount(createAccountCommand);
        }
        return userBasicDTO;
    }

    /**
     * 给员工创建会员账号
     *
     * @param employee
     * @return
     */
    private MemberBasicDTO
    createMemberAccount(Employee employee) {
        MemberQuery query = new MemberQuery();
        query.setPhone(employee.getPhone().getNumber());
        query.setOrganizationId(employee.getOrganizationId().getId());
        query.setSystemId(1L);
        MemberBasicDTO memberBasicDTO = memberClient.getBasicSystemMember(query);
        if (memberBasicDTO == null) {
            CreateMemberCommand createMemberCommand = new CreateMemberCommand();
            createMemberCommand.setOrganizationId(employee.getOrganizationId().getId());
            createMemberCommand.setSystemId("1");
            createMemberCommand.setAuditStatus(1);
            createMemberCommand.setPassword("12345678");
            createMemberCommand.setName(employee.getName().getName());
            createMemberCommand.setPhone(employee.getPhone() != null ? employee.getPhone().getNumber() : null);
            createMemberCommand.setMemberType(1);
            MemberIdDTO member = memberClient.createMember(createMemberCommand);
            memberBasicDTO = new MemberBasicDTO();
            memberBasicDTO.setAccountId(member.getAccountId());
            memberBasicDTO.setId(member.getId());
        }
        return memberBasicDTO;
    }

    @Override
    public EmployeeId createOrganizationCreatorEmployee(OrganizationId organizationId, Long departmentId, Long userId, List<Long> roleIds) {
        //切换数据库
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(organizationId.getId());
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        // UserBasicDTO userBasicDTO = userClient.getBasicUserById(userId);
        AccountBasicDTO accountBasicDTO = accountClient.getBasicAccount(userId);
        MemberBasicDTO memberBasicDTO  = memberClient.getBasicAccountSystemMember(userId, Long.valueOf(arkSystemId), organizationId.getId());
//        for (MemberBasicDTO dto : dtos){
//            if (dto.getOrganizationId() == organizationId.getId().longValue()){
//                accountBasicDTO.setName(dto.getName());
//            }
//        }
        Department department = departmentDomainRepository.findById(new DepartmentId(departmentId));
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .userId(new UserId(accountBasicDTO.getId()))
                .organizationId(organizationId)
                .name(new PinyinName(memberBasicDTO.getName()))
                .phone(new PhoneNumber(memberBasicDTO.getPhone()))
                .avatar(new Avatar(memberBasicDTO.getAvatar()))
                // .email(StringUtils.isNotBlank(accountBasicDTO.getEmail()) ? new Email(accountBasicDTO.getEmail()) : null)
                .gender(null)
                .jobStatus(JobStatus.ON_JOB)
                .identityType(EmployeeIdentityType.CREATOR)
                .createTime(LocalDateTime.now())
                .memberId(new MemberId(memberBasicDTO.getId()))
                .department(department)
                .build();

        EmployeeId employeeId = employeeDomainRepository.saveInfo(employeeInfo);

        //分配角色
        if (roleIds != null && !roleIds.isEmpty()) {
            AssignRoleToEmployeeCommand assignRoleToEmployeeCommand = new AssignRoleToEmployeeCommand();
            assignRoleToEmployeeCommand.setEmployeeId(employeeId.getId());
            assignRoleToEmployeeCommand.setOrganizationId(organizationId.getId());
            assignRoleToEmployeeCommand.setUserId(employeeInfo.getUserId().getId());
            assignRoleToEmployeeCommand.setRoleIds(roleIds);
            roleManageClient.assignRoleToEmployee(assignRoleToEmployeeCommand);
        }
        //清除数据库
        DynamicDataSourceContextHolder.clear();
        return employeeId;
    }

    @Override
    public void updateOrganizationCreatorRole(OrganizationId organizationId, List<Long> roleIds) {
        //切换数据库
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(organizationId.getId());
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }

        Employee organizationCreator = employeeDomainRepository.findOrganizationCreator(organizationId);

        //给超管分配角色
        AssignRoleToEmployeeCommand assignRoleToEmployeeCommand = new AssignRoleToEmployeeCommand();
        assignRoleToEmployeeCommand.setEmployeeId(organizationCreator.getId().getId());
        assignRoleToEmployeeCommand.setRoleIds(roleIds);
        assignRoleToEmployeeCommand.setOrganizationId(organizationId.getId());
        roleManageClient.assignRoleToEmployee(assignRoleToEmployeeCommand);

        //给管理员重新授权
        List<Employee> admins = employeeDomainRepository.findOrganizationAdmins(organizationId);
        for (Employee admin : admins) {
            AssignRoleToEmployeeCommand adminAssignRoleToEmployeeCommand = new AssignRoleToEmployeeCommand();
            adminAssignRoleToEmployeeCommand.setEmployeeId(admin.getId().getId());
            adminAssignRoleToEmployeeCommand.setUserId(admin.getUserId().getId());
            adminAssignRoleToEmployeeCommand.setOrganizationId(organizationId.getId());
            adminAssignRoleToEmployeeCommand.setRoleIds(roleIds);
            roleManageClient.assignRoleToEmployee(adminAssignRoleToEmployeeCommand);
        }
        //清除数据库
        DynamicDataSourceContextHolder.clear();
    }

    @Override
    public void changeOrganizationCreator(OrganizationId organizationId, Long newCreatorId, List<Long> roleIds) {
        //切换数据库
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(organizationId.getId());
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        //去除原来的组织创始人的身份以及权限
        Employee oldCreator = employeeDomainRepository.findOrganizationCreator(organizationId);
        oldCreator.cancelCreatorIdentity();
        employeeDomainRepository.save(oldCreator);

        RbacRevokeUserRoleCommand revokeUserRoleCommand = new RbacRevokeUserRoleCommand();
        revokeUserRoleCommand.setUserId(oldCreator.getUserId().getId().toString());
        revokeUserRoleCommand.setContext(OrganizationSessionContext.contextId);
        revokeUserRoleCommand.setContextId(organizationId.getId().toString());
        roleClient.revokeUserRole(revokeUserRoleCommand);

        //更改创始人
        Employee newCreator = employeeDomainRepository.findByUser(organizationId, newCreatorId);
        if (newCreator == null) {
            Long departmentId = departmentDomainRepository.findTopByOrganizationId(organizationId.getId());
            EmployeeId employeeId = this.createOrganizationCreatorEmployee(organizationId, departmentId, newCreatorId, roleIds);
            newCreator = employeeDomainRepository.findById(employeeId);
        }
        else {
            newCreator.changeToCreatorIdentity();
            employeeDomainRepository.save(newCreator);

            //给超管分配角色
            AssignRoleToEmployeeCommand assignRoleToEmployeeCommand = new AssignRoleToEmployeeCommand();
            assignRoleToEmployeeCommand.setEmployeeId(newCreator.getId().getId());
            assignRoleToEmployeeCommand.setUserId(newCreator.getUserId().getId());
            assignRoleToEmployeeCommand.setOrganizationId(organizationId.getId());
            assignRoleToEmployeeCommand.setRoleIds(roleIds);
            roleManageClient.assignRoleToEmployee(assignRoleToEmployeeCommand);
        }

        //给管理员重新授权
        List<Employee> admins = employeeDomainRepository.findOrganizationAdmins(organizationId);
        for (Employee admin : admins) {
            AssignRoleToEmployeeCommand adminAssignRoleToEmployeeCommand = new AssignRoleToEmployeeCommand();
            adminAssignRoleToEmployeeCommand.setEmployeeId(admin.getId().getId());
            adminAssignRoleToEmployeeCommand.setUserId(admin.getUserId().getId());
            adminAssignRoleToEmployeeCommand.setOrganizationId(organizationId.getId());
            adminAssignRoleToEmployeeCommand.setRoleIds(roleIds);
            roleManageClient.assignRoleToEmployee(adminAssignRoleToEmployeeCommand);
        }
        //清除数据库
        DynamicDataSourceContextHolder.clear();
    }

    @Override
    public EmployeeId joinOrganization(UserId userId, OrganizationId organizationId, Long inviterId, String inviterName) {
        UserBasicDTO userBasicDTO = userClient.getBasicUserById(userId.getId());
        if (employeeDomainRepository.findByUser(organizationId, userId.getId()) != null) {
            throw new ValidationException("用户已经加入组织请勿重复加入");
        }
        if (employeeDomainRepository.countByOrganizationAndEmployeePhone(organizationId, userBasicDTO.getPhone(), null) > 0) {
            throw new ValidationException("组织下已经存在此手机号对应的员工");
        }

        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .userId(new UserId(userBasicDTO.getId()))
                .organizationId(organizationId)
                .name(new PinyinName(userBasicDTO.getName()))
                .phone(new PhoneNumber(userBasicDTO.getPhone()))
                .avatar(new Avatar(userBasicDTO.getAvatar()))
                .email(StringUtils.isNotBlank(userBasicDTO.getEmail()) ? new Email(userBasicDTO.getEmail()) : null)
                .gender(null)
                .jobStatus(JobStatus.ON_JOB)
                .identityType(EmployeeIdentityType.EMPLOYEE)
                .createTime(LocalDateTime.now())
                .build();

        return employeeDomainRepository.saveInfo(employeeInfo);
    }

    /**
     * 获取员工授权的权限
     *
     * @param employee
     * @param menuPermissions
     * @param identity
     * @return
     */
    private List<PermissionWithRuleDTO> getAuthorizePermissions(Employee employee, List<MenuCheckWithPermissionDTO> menuPermissions, Identity identity) {
        //转换前台提交的菜单勾选情况为授权的权限节点
        List<PermissionWithRuleDTO> rolePermissions = roleClient.getRolePermissions(employee.getAuthItem());
        List<PermissionWithRuleDTO> userPermissions = authClient.getUserPermissions(identity.getId().toString(), OrganizationSessionContext.contextId, employee.getOrganizationId().getId().toString());
        List<MenuDTO> menuDTOS = menuClient.getPermissionParentMenus(identity.getId().toString(), OrganizationSessionContext.contextId, employee.getOrganizationId().getId().toString());
        List<PermissionWithRuleDTO> permissions = new ArrayList<>();
        for (MenuCheckWithPermissionDTO menuPermission : menuPermissions) {
            MenuDTO parentMenu = menuDTOS.stream().filter(m -> m.getId().equals(menuPermission.getId())).findFirst().orElse(null);
            if (parentMenu == null) { continue; }

            //全勾
            if (menuPermission.getChecked() == CheckedType.FULL.getId()) {
                //如果前端没有勾选权限，则使用用户的权限结合权限组自己的权限
                if (menuPermission.getPermissions() == null || menuPermission.getPermissions().isEmpty()) {
                    for (MenuDTO child : parentMenu.getChildren()) {
                        PermissionWithRuleDTO rolePermission = rolePermissions.stream().filter(rp -> rp.getPermission().equals(child.getAuthItem())).findFirst().orElse(null);
                        PermissionWithRuleDTO userPermission = userPermissions.stream().filter(rp -> rp.getPermission().equals(child.getAuthItem())).findFirst().orElse(null);
                        if (userPermission == null) { continue; }
                        permissions.add(rolePermission != null ? rolePermission : userPermission);
                    }
                }
                //如果前端勾选了权限，则使用前端勾选的权限
                else {
                    permissions.addAll(menuPermission.getPermissions());
                }
            }
            //半勾
            else if (menuPermission.getChecked() == CheckedType.HALF.getId()) {
                //如果前端没有勾选权限，则使用权限组自己的权限
                if (menuPermission.getPermissions() == null || menuPermission.getPermissions().isEmpty()) {
                    for (MenuDTO child : parentMenu.getChildren()) {
                        PermissionWithRuleDTO rolePermission = rolePermissions.stream().filter(rp -> rp.getPermission().equals(child.getAuthItem())).findFirst().orElse(null);
                        if (rolePermission == null) { continue; }
                        permissions.add(rolePermission);
                    }
                }
                else {
                    permissions.addAll(menuPermission.getPermissions());
                }
            }
        }
        return permissions;
    }
}
