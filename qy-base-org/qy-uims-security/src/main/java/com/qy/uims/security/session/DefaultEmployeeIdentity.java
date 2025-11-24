package com.qy.uims.security.session;

import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.dto.MenuBasicDTO;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.action.Action;
import com.qy.security.permission.rule.EmployeeHasPermission;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认的员工身份实现
 *
 * @author legendjw
 */
public class DefaultEmployeeIdentity implements EmployeeIdentity {
    private Long id;
    private String name;
    private Long organizationId;
    private Long departmentId;
    private Long systemId;
    private Long userId;
    private Long employeeId;
    private AuthClient authClient;
    private MenuClient menuClient;

    public DefaultEmployeeIdentity(Long id, String name, Long organizationId, Long departmentId, Long systemId, Long userId, Long employeeId,
                                   AuthClient authClient, MenuClient menuClient) {
        this.id = id;
        this.name = name;
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.systemId = systemId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.authClient = authClient;
        this.menuClient = menuClient;

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public Long getDepartmentId() {
        return departmentId;
    }

    @Override
    public Long getSystemId() {
        return systemId;
    }

    @Override
    public Long getEmployeeId() {
        return employeeId;
    }

    @Override
    public boolean hasPermission(String permission) {
        return authClient.isUserHasPermission(userId.toString(), OrganizationSessionContext.contextId, organizationId.toString(), permission,
                1L);
    }

    @Override
    public boolean hasPermission(GetPermission permission) {
        return hasPermission(permission.getPermission());
    }

    @Override
    public boolean hasPermission(EmployeeHasPermission employeeHasPermission, String permission, Object id) {
        return employeeHasPermission.hasPermission(this, permission, id);
    }

    @Override
    public boolean hasPermission(EmployeeHasPermission employeeHasPermission, GetPermission permission, Object id) {
        return hasPermission(employeeHasPermission, permission.getPermission(), id);
    }

    @Override
    public List<Action> getActions(String code) {
        List<MenuBasicDTO> menuBasicDTOS = menuClient.getAuthByCode(code, 1);
        List<Action> actions = new ArrayList<>();
        if (menuBasicDTOS == null){
            return actions;
        }
        menuBasicDTOS.stream().map(menuBasicDTO -> {
            if (hasPermission(menuBasicDTO.getAuthItem())) {
                List<String> ids = Arrays.asList(menuBasicDTO.getAuthItem().split("/"));
                Action action = new Action(ids.get(ids.size() - 1), menuBasicDTO.getName());
                actions.add(action);
            }
            return menuBasicDTO;
        }).collect(Collectors.toList());
        return actions;
    }

    @Override
    public boolean hasPermission(String code, String permissionAction) {
        List<MenuBasicDTO> menuBasicDTOS = menuClient.getAllAuthByCode(code);
        if (menuBasicDTOS == null){
            return false;
        }
        for (MenuBasicDTO dto : menuBasicDTOS){
            if (permissionAction.equals(dto.getAuthItem().substring(dto.getAuthItem().lastIndexOf("/") + 1))){
                return hasPermission(dto.getAuthItem());
            }
        }
        return false;
    }
}
