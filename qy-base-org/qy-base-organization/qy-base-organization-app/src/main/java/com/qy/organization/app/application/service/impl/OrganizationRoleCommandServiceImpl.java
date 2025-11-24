package com.qy.organization.app.application.service.impl;

import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.dto.MenuCheckWithPermissionDTO;
import com.qy.organization.app.application.enums.CheckedType;
import com.qy.organization.app.application.service.OrganizationRoleCommandService;
import com.qy.organization.app.infrastructure.persistence.RoleDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.client.RoleClient;
import com.qy.rbac.api.command.*;
import com.qy.rbac.api.dto.MenuDTO;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.util.PinyinUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组命令实现
 *
 * @author legendjw
 */
@Service
public class OrganizationRoleCommandServiceImpl implements OrganizationRoleCommandService {
    private RoleDataRepository roleDataRepository;
    private EmployeeClient employeeClient;
    private RoleClient roleClient;
    private MenuClient menuClient;
    private AuthClient authClient;
    private MemberClient memberClient;

    public OrganizationRoleCommandServiceImpl(RoleDataRepository roleDataRepository, EmployeeClient employeeClient,
                                              RoleClient roleClient, MenuClient menuClient, AuthClient authClient,
                                              MemberClient memberClient) {
        this.roleDataRepository = roleDataRepository;
        this.employeeClient = employeeClient;
        this.roleClient = roleClient;
        this.menuClient = menuClient;
        this.authClient = authClient;
        this.memberClient = memberClient;
    }

    @Override
    @Transactional
    public Long createRole(CreateRoleCommand command) {
        if (roleDataRepository.countByOrganizationIdAndName(command.getOrganizationId(), command.getContext(), command.getContextId(), command.getName(), null) > 0) {
            throw new ValidationException("组织下已经存在同名的权限组，请更换新的权限组名称");
        }

        EmployeeIdentity employee = command.getEmployee();
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(command, roleDO, "employee");
//        if (!command.getContext().equals("member_system")){
//            roleDO.setContextId(command.getOrganizationId().toString());
//        }
        roleDO.setNamePinyin(PinyinUtils.getAlpha(roleDO.getName()));
        roleDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        roleDO.setCreatorId(employee.getId());
        roleDO.setCreatorName(employee.getName());
        roleDO.setCreateTime(LocalDateTime.now());
        roleDataRepository.save(roleDO);

        //创建rbac权限组
        RbacCreateRoleCommand rbacCreateRoleCommand = new RbacCreateRoleCommand();
        rbacCreateRoleCommand.setId(String.format("role_%s", roleDO.getId()));
        rbacCreateRoleCommand.setDescription(roleDO.getName());
        roleClient.createRole(rbacCreateRoleCommand);
        roleDO.setAuthItem(rbacCreateRoleCommand.getId());
        roleDataRepository.save(roleDO);

        return roleDO.getId();
    }

    @Override
    @Transactional
    public Long copyRole(CopyRoleCommand command) {
        RoleDO copyRoleDO = roleDataRepository.findById(command.getCopyRoleId());
        if (copyRoleDO == null) {
            throw new NotFoundException("未找到指定复制的权限组");
        }
        CreateRoleCommand createRoleCommand = new CreateRoleCommand();
        createRoleCommand.setOrganizationId(copyRoleDO.getOrganizationId());
        BeanUtils.copyProperties(command, createRoleCommand, "copyRoleId");
        Long roleId = createRole(createRoleCommand);
        RoleDO newRole = roleDataRepository.findById(roleId);

        //复制权限组权限
        List<PermissionWithRuleDTO> permissions = roleClient.getRolePermissions(copyRoleDO.getAuthItem());
        RbacAuthorizeRoleCommand rbacAuthorizeRoleCommand = new RbacAuthorizeRoleCommand();
        rbacAuthorizeRoleCommand.setPermissions(permissions);
        roleClient.authorizeRole(newRole.getAuthItem(), rbacAuthorizeRoleCommand);

        return roleId;
    }

    @Override
    @Transactional
    public void updateRole(UpdateRoleCommand command) {
        RoleDO roleDO = roleDataRepository.findById(command.getId());
        if (roleDO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }
        if (roleDataRepository.countByOrganizationIdAndName(roleDO.getOrganizationId(), command.getContext(), command.getContextId(), command.getName(), roleDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的权限组，请更换新的权限组名称");
        }
        EmployeeIdentity employee = command.getEmployee();

        BeanUtils.copyProperties(command, roleDO, "employee");
        roleDO.setNamePinyin(PinyinUtils.getAlpha(roleDO.getName()));
        roleDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        roleDO.setUpdatorId(employee.getId());
        roleDO.setUpdatorName(employee.getName());
        roleDO.setUpdateTime(LocalDateTime.now());
        roleDataRepository.save(roleDO);

        //编辑rbac权限组
        RbacUpdateRoleCommand rbacUpdateRoleCommand = new RbacUpdateRoleCommand();
        rbacUpdateRoleCommand.setNewId(roleDO.getAuthItem());
        rbacUpdateRoleCommand.setDescription(roleDO.getName());
        roleClient.updateRole(roleDO.getAuthItem(), rbacUpdateRoleCommand);
    }

    @Override
    @Transactional
    public void deleteRole(DeleteRoleCommand command) {
        EmployeeIdentity employee = command.getEmployee();
        RoleDO roleDO = roleDataRepository.findById(command.getId());
        if (roleDO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }
        List<Long> userIds = roleDataRepository.findRoleUserIds(roleDO.getId());
        if (!userIds.isEmpty()) {
            throw new ValidationException("指定权限组已被使用无法删除");
        }

        roleDataRepository.remove(roleDO.getId(), employee);

        //删除rbac权限组
        roleClient.deleteRole(roleDO.getAuthItem());
    }

    @Override
    public void authorizeRole(AuthorizeRoleCommand command, Identity identity) {
        RoleDO roleDO = roleDataRepository.findById(command.getId());
        if (roleDO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }
        //转换前台提交的菜单勾选情况为授权的权限节点
        List<MenuCheckWithPermissionDTO> menuPermissions = command.getMenuPermissions();
        List<PermissionWithRuleDTO> rolePermissions = roleClient.getRolePermissions(roleDO.getAuthItem());
        List<PermissionWithRuleDTO> userPermissions = authClient.getUserPermissions(identity.getId().toString(), OrganizationSessionContext.contextId, roleDO.getOrganizationId().toString());
        List<MenuDTO> menuDTOS = menuClient.getPermissionParentMenus(identity.getId().toString(), OrganizationSessionContext.contextId, roleDO.getOrganizationId().toString());
        List<PermissionWithRuleDTO> permissions = new ArrayList<>();
        for (MenuCheckWithPermissionDTO menuPermission : menuPermissions) {
            MenuDTO parentMenu = menuDTOS.stream().filter(m -> m.getId().equals(menuPermission.getId())).findFirst().orElse(null);
            if (parentMenu == null) {
                continue;
            }

            //全勾
            if (menuPermission.getChecked() == CheckedType.FULL.getId()) {
                //如果前端没有勾选权限，则使用用户的权限结合权限组自己的权限
                if (menuPermission.getPermissions() == null || menuPermission.getPermissions().isEmpty()) {
                    for (MenuDTO child : parentMenu.getChildren()) {
                        PermissionWithRuleDTO rolePermission = rolePermissions.stream().filter(rp -> rp.getPermission().equals(child.getAuthItem())).findFirst().orElse(null);
                        PermissionWithRuleDTO userPermission = userPermissions.stream().filter(rp -> rp.getPermission().equals(child.getAuthItem())).findFirst().orElse(null);
                        if (userPermission == null) {
                            continue;
                        }
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
                        if (rolePermission == null) {
                            continue;
                        }
                        permissions.add(rolePermission);
                    }
                } else {
                    permissions.addAll(menuPermission.getPermissions());
                }
            }
        }

        //使用权限节点进行授权
        RbacAuthorizeRoleCommand rbacAuthorizeRoleCommand = new RbacAuthorizeRoleCommand();
        rbacAuthorizeRoleCommand.setPermissions(permissions);
        roleClient.authorizeRole(roleDO.getAuthItem(), rbacAuthorizeRoleCommand);
    }

    @Override
    public void assignRoleToEmployee(AssignRoleToEmployeeCommand command) {
        EmployeeBasicDTO employee;
        if (command.getOrganizationId() == null) {// 2024年07月17日17:12:25-lxl处理，开户时给超管跨库分配权限问题
            employee = employeeClient.getBasicEmployeeById(command.getEmployeeId());
        } else {
            employee = employeeClient.getBasicEmployeeByOrganizationId(command.getEmployeeId(), command.getOrganizationId());
        }

        if (employee == null) {
            throw new NotFoundException("未找到需要授权的员工");
        }
        if (employee.getUserId() == null || employee.getUserId() == 0) {// 2024年07月16日16:00:38 lxl添加
            employee.setUserId(command.getUserId());
        }
        List<RoleDO> roleDOS = roleDataRepository.findByIds(command.getRoleIds());
        roleDataRepository.saveRoleUser(employee, roleDOS);

        RbacAssignRoleToUserCommand assignRoleToUserCommand = new RbacAssignRoleToUserCommand();
        assignRoleToUserCommand.setUserId(employee.getUserId().toString());
        assignRoleToUserCommand.setRoleIds(roleDOS.stream().map(RoleDO::getAuthItem).collect(Collectors.toList()));
        assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
        assignRoleToUserCommand.setContextId(employee.getOrganizationId().toString());
        MemberBasicDTO dto = memberClient.getBasicMember(employee.getMemberId());
        if (dto != null && dto.getSystemId() != 1L) {
            assignRoleToUserCommand.setSystemId(dto.getSystemId());
        }
        roleClient.assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    public void assignRoleToUser(AssignRoleToUserCommand command) {
        List<RoleDO> roleDOS = new ArrayList<>();
        if (!command.getRoleIds().isEmpty()) {
            roleDOS = roleDataRepository.findByIds(command.getRoleIds());
            roleDataRepository.saveRoleUser(command.getOrganizationId(), command.getSystemId(), command.getUserId(), command.getUserName(), roleDOS);
        } else {
            roleDataRepository.saveRoleUser(command.getOrganizationId(), command.getSystemId(), command.getUserId(), command.getUserName(), roleDOS);
        }

        RbacAssignRoleToUserCommand assignRoleToUserCommand = new RbacAssignRoleToUserCommand();
        assignRoleToUserCommand.setUserId(command.getUserId().toString());
        assignRoleToUserCommand.setRoleIds(roleDOS.stream().map(RoleDO::getAuthItem).collect(Collectors.toList()));
        assignRoleToUserCommand.setContext(command.getContext());
        assignRoleToUserCommand.setContextId(command.getContextId());
        assignRoleToUserCommand.setSystemId(command.getSystemId());
        roleClient.assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    public void revokeEmployeeRole(Long employeeId) {
        EmployeeBasicDTO employee = employeeClient.getBasicEmployeeById(employeeId);
        if (employee == null) {
            return;
        }
        roleDataRepository.removeRoleUser(employee.getOrganizationId(), employee.getUserId());

        //撤销员工的所有权限
        RbacRevokeUserRoleCommand revokeUserRoleCommand = new RbacRevokeUserRoleCommand();
        revokeUserRoleCommand.setUserId(employee.getUserId().toString());
        revokeUserRoleCommand.setContext(OrganizationSessionContext.contextId);
        revokeUserRoleCommand.setContextId(employee.getOrganizationId().toString());
        roleClient.revokeUserRole(revokeUserRoleCommand);
    }

    @Override
    public void setDefaultRole(SetDefaultRoleCommand command) {
        RoleDO roleDO = roleDataRepository.findById(command.getId());
        if (roleDO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }
        roleDataRepository.setDefaultRole(roleDO, command.getDefaultRole());
    }

//    @Override
//    public void setDefaultRoleForMemberSystem(SetDefaultRoleForMemberCommand command) {
//        RoleDO roleDO = roleDataRepository.findByIdAndContextAndContextId(command.getId(),command.getContext(),command.getContextId());
//        if (roleDO == null) {
//            throw new NotFoundException("未找到指定的权限组");
//        }
//        roleDataRepository.setDefaultRoleForMemberSystem(roleDO, command.getDefaultRole());
//    }
}