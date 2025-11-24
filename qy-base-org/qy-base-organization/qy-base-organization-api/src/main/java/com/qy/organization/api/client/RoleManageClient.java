package com.qy.organization.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.organization.api.command.AssignRoleToEmployeeCommand;
import com.qy.organization.api.command.AssignRoleToUserCommand;
import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.organization.api.dto.RoleDTO;
import com.qy.organization.api.dto.RoleUserDTO;
import com.qy.organization.api.query.RoleQuery;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限组客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-organization-role", configuration = FeignTokenRequestInterceptor.class)
public interface RoleManageClient {
    /**
     * 获取组织权限组列表
     *
     * @return
     */
    @GetMapping("/v4/api/organization/roles")
    SimplePageImpl<RoleDTO> getRoles(@SpringQueryMap RoleQuery query);

    /**
     * 获取指定用户的权限组
     *
     * @return
     */
    @GetMapping("/v4/api/organization/roles/by-user")
    List<RoleBasicDTO> getRolesByUser(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "system_id") Long systemId
    );

    /**
     * 获取权限组下的用户
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/organization/roles/{id}/users")
    List<RoleUserDTO> getRoleUsers(
            @PathVariable(value = "id") Long id
    );

    /**
     * 分配权限组给指定用户
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/organization/roles/assign-role-to-employee")
    void assignRoleToEmployee(
            @Valid @RequestBody AssignRoleToEmployeeCommand command
    );

    /**
     * 分配权限组给指定用户
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/organization/roles/assign-role-to-user")
    void assignRoleToUser(
            @Valid @RequestBody AssignRoleToUserCommand command
    );

    /**
     * 删除指定员工的权限组
     *
     * @return
     */
    @PostMapping("/v4/api/organization/roles/revoke-employee-role")
    public ResponseEntity<Object> revokeEmployeeRole(
            @RequestParam(value = "employee_id") Long employeeId
    );

    /**
     * 获取指定的默认权限组
     *
     * @param systemId
     * @param organizationId
     * @return
     */
    @GetMapping("/v4/api/organization/roles/default-role")
    RoleBasicDTO getDefaultRole(
            @RequestParam(value = "system_id") Long systemId,
            @RequestParam(value = "organization_id") Long organizationId
    );


    /**
     * 获取指定的默认权限组
     * @param authItem
     * @return
     */
    @GetMapping("/v4/api/organization/roles/auth-item")
    RoleBasicDTO getByAuthItem(
            @RequestParam(value = "auth_item") String authItem
    );

}