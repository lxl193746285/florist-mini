package com.qy.organization.app.application.service.impl;

import com.qy.organization.app.application.assembler.RoleAssembler;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.enums.CheckedType;
import com.qy.organization.app.application.query.RoleQuery;
import com.qy.organization.app.application.security.RolePermission;
import com.qy.organization.app.application.service.OrganizationQueryService;
import com.qy.organization.app.application.service.RoleQueryService;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.organization.app.infrastructure.persistence.RoleDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.UserRoleDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.RoleMapper;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.client.RoleClient;
import com.qy.rbac.api.dto.MenuDTO;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组查询服务实现
 *
 * @author legendjw
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {
    @Autowired
    private RoleMapper roleMapper;
    private RoleAssembler roleAssembler;
    private RoleDataRepository roleDataRepository;
    private RoleClient roleClient;
    private RolePermission rolePermission;
    private OrganizationQueryService organizationQueryService;
    private MenuClient menuClient;

    public RoleQueryServiceImpl(RoleAssembler roleAssembler, RoleDataRepository roleDataRepository, RoleClient roleClient, RolePermission rolePermission, OrganizationQueryService organizationQueryService, MenuClient menuClient) {
        this.roleAssembler = roleAssembler;
        this.roleDataRepository = roleDataRepository;
        this.roleClient = roleClient;
        this.rolePermission = rolePermission;
        this.organizationQueryService = organizationQueryService;
        this.menuClient = menuClient;
    }

    @Override
    public Page<RoleDTO> getRoles(RoleQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = rolePermission.getFilterQuery(identity, RolePermission.LIST);
        Page<RoleDO> roleDOPage = roleDataRepository.findByQuery(query, filterQuery);
        List<OrganizationBasicDTO> organizationBasicDTOS = roleDOPage.getRecords().isEmpty() ?
                new ArrayList<>() :
                organizationQueryService.getBasicOrganizationsByIds(roleDOPage.getRecords().stream()
                        .map(RoleDO::getOrganizationId).collect(Collectors.toList()));
        return roleDOPage.map(role -> roleAssembler.toDTO(role, organizationBasicDTOS, identity));
    }

    @Override
    public List<RoleBasicDTO> getBasicRoles(List<Long> ids) {
        List<RoleDO> roleDOS = roleDataRepository.findByIds(ids);
        return roleDOS.stream().map(role -> roleAssembler.toBasicDTO(role)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        RoleDO roleDO = roleDataRepository.findById(id);
        List<OrganizationBasicDTO> organizationBasicDTOS = roleDO == null ? new ArrayList<>() : organizationQueryService.getBasicOrganizationsByIds(Arrays.asList(roleDO.getOrganizationId()));
        return roleAssembler.toDTO(roleDO, organizationBasicDTOS, null);
    }

    @Override
    public RoleBasicDTO getRoleByAuthItem(String authItem) {
        RoleDO roleDO = roleDataRepository.findByAuthItem(authItem);
        if (roleDO != null) {
            return roleAssembler.toBasicDTO(roleDO);
        }
        return null;
    }

    @Override
    public RoleDTO getRoleById(Long id, Identity identity) {
        RoleDO roleDO = roleDataRepository.findById(id);
        List<OrganizationBasicDTO> organizationBasicDTOS = roleDO == null ? new ArrayList<>() : organizationQueryService.getBasicOrganizationsByIds(Arrays.asList(roleDO.getOrganizationId()));
        return roleAssembler.toDTO(roleDO, organizationBasicDTOS, identity);
    }

    @Override
    public RoleBasicDTO getDefaultRole(DefaultRole defaultRole, Long systemId, Long organizationId) {
        RoleDO roleDO = roleDataRepository.findByDefaultRole(defaultRole, systemId, organizationId);
        return roleAssembler.toBasicDTO(roleDO);
    }

    @Override
    public List<PermissionWithRuleDTO> getRolePermissions(Long roleId) {
        RoleDO roleDO = roleDataRepository.findById(roleId);
        if (roleDO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }

        return roleClient.getRolePermissions(roleDO.getAuthItem());
    }

    @Override
    public RoleMenuPermissionDTO getRoleMenuPermission(Long roleId, Identity identity) {
        RoleDO roleDO = roleDataRepository.findById(roleId);
        if (roleDO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }
        RoleMenuPermissionDTO roleMenuPermissionDTO = new RoleMenuPermissionDTO();
        //获取权限组下权限
        List<PermissionWithRuleDTO> permissionWithRuleDTOS = roleClient.getRolePermissions(roleDO.getAuthItem());
        List<String> permissions = permissionWithRuleDTOS.stream().map(PermissionWithRuleDTO::getPermission).collect(Collectors.toList());
        List<MenuDTO> menuDTOS = menuClient.getPermissionParentMenus(identity.getId().toString(),
                OrganizationSessionContext.contextId, roleDO.getOrganizationId().toString());

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
    public List<RoleBasicDTO> getRolesByOrganizationAndUser(Long organizationId, Long userId, Long systemId) {
        List<RoleDO> roleDOS = roleDataRepository.findByOrganizationAndUser(organizationId, userId, systemId);
        return roleDOS.stream().map(role -> roleAssembler.toBasicDTO(role)).collect(Collectors.toList());
    }

    @Override
    public List<RoleUserDTO> getRoleUsers(Long roleId) {
        List<UserRoleDO> userRoleDOS = roleDataRepository.findRoleUsers(roleId);
        return userRoleDOS.isEmpty() ? new ArrayList<>() : userRoleDOS.stream().map(u -> new RoleUserDTO(u.getUserId(), u.getUserName())).collect(Collectors.toList());
    }

    @Override
    public Page<AuthRoleUserDTO> getAuthUsers(Long id, AuthRoleUserQueryDTO query) {
        IPage<AuthRoleUserDTO> iPage = roleMapper.getAuthUsers(new PageDTO<>(query.getPage(), query.getPerPage()), query);

        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }
}
