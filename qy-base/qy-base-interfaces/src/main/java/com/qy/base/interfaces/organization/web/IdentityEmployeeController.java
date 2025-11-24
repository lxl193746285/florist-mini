package com.qy.base.interfaces.organization.web;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.identity.api.client.UserClient;
import com.qy.member.api.client.AccountClient;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.AccountBasicDTO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.api.dto.OrganizationWithIdentityDTO;
import com.qy.organization.app.application.command.SwitchOrganizationCommand;
import com.qy.organization.app.application.command.UpdateIdentityEmployeeCommand;
import com.qy.organization.app.application.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.dto.EmployeeUserDTO;
import com.qy.organization.app.application.service.EmployeeCommandService;
import com.qy.organization.app.application.service.EmployeeQueryService;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.exception.BusinessException;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 认证员工
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/employee/employee")
public class IdentityEmployeeController {
    private OrganizationSessionContext sessionContext;
    private MemberSystemSessionContext memberSystemSessionContext;
    private EmployeeQueryService employeeQueryService;
    private EmployeeCommandService employeeCommandService;
    private OrganizationClient organizationClient;
    private UserClient userClient;
    private AuthClient authClient;
    private MemberClient memberClient;
    private AccountClient accountClient;

    public IdentityEmployeeController(OrganizationSessionContext sessionContext, EmployeeQueryService employeeQueryService,
                                      EmployeeCommandService employeeCommandService, OrganizationClient organizationClient,
                                      UserClient userClient, AuthClient authClient, MemberClient memberClient, AccountClient accountClient,
                                      MemberSystemSessionContext memberSystemSessionContext) {
        this.sessionContext = sessionContext;
        this.employeeQueryService = employeeQueryService;
        this.employeeCommandService = employeeCommandService;
        this.organizationClient = organizationClient;
        this.userClient = userClient;
        this.authClient = authClient;
        this.memberClient = memberClient;
        this.accountClient = accountClient;
        this.memberSystemSessionContext = memberSystemSessionContext;
    }

    /**
     * 获取当前员工用户信息
     *
     * 如果员工有组织，则返回当前组织下的员工信息否则返回全局用户信息
     *
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<EmployeeUserDTO> getEmployee() {
        Identity identity = sessionContext.getUser();
        List<OrganizationBasicDTO> userJoinOrganizations = organizationClient.getUserJoinOrganizations(identity.getId());
        EmployeeUserDTO employeeUserDTO = new EmployeeUserDTO();
        if (userJoinOrganizations.isEmpty()) {
            AccountBasicDTO userBasicDTO = accountClient.getBasicAccount(identity.getId());
            employeeUserDTO.setType("user");
            employeeUserDTO.setId(userBasicDTO.getId());
            employeeUserDTO.setName(userBasicDTO.getName());
            employeeUserDTO.setAvatar(userBasicDTO.getAvatar());
            employeeUserDTO.setPhone(userBasicDTO.getPhone());
        }
        else {
            employeeUserDTO.setType("employee");
            EmployeeIdentity employeeIdentity = sessionContext.getEmployee();
            AccountBasicDTO userBasicDTO = accountClient.getBasicAccount(identity.getId());
            employeeUserDTO.setId(employeeIdentity.getId());
            employeeUserDTO.setName(employeeIdentity.getName());
            employeeUserDTO.setAvatar(userBasicDTO.getAvatar());
            employeeUserDTO.setPhone(userBasicDTO.getPhone());
        }
        return ResponseUtils.ok().body(employeeUserDTO);
    }

    /**
     * 获取当前用户指定组织下的员工信息
     *
     * @param organizationId 组织id
     * @return
     */
    @GetMapping
    public ResponseEntity<EmployeeBasicDTO> getEmployee(
            @RequestParam(value = "organization_id", required = false) Long organizationId
    ) {
        EmployeeIdentity identity = organizationId != null ? sessionContext.getEmployee(organizationId) : sessionContext.getEmployee();
        if (identity == null) {
            throw new BusinessException("获取非法的组织员工信息");
        }
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployeeById(identity.getId()));
    }

    /**
     * 获取当前用户加入的组织列表
     *
     * @return
     */
    @GetMapping("/join-organizations")
    public ResponseEntity<List<OrganizationBasicDTO>> getJoinOrganizations() {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(organizationClient.getUserJoinOrganizations(identity.getId()));
    }

    /**
     * 获取当前用户加入的组织以及对应的身份列表
     *
     * @return
     */
    @GetMapping("/join-organizations-with-identity")
    public ResponseEntity<List<OrganizationWithIdentityDTO>> getJoinOrganizationsWithIdentity() {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(organizationClient.getUserJoinOrganizationsWithIdentity(identity.getId()));
    }

    /**
     * 获取当前的激活的组织
     *
     * @return
     */
    @GetMapping("/current-organization")
    public ResponseEntity<OrganizationBasicDTO> getCurrentOrganization() {
        EmployeeIdentity identity = sessionContext.getEmployee();
        return ResponseUtils.ok().body(organizationClient.getBasicOrganizationById(identity.getOrganizationId()));
    }

    /**
     * 获取当前用户有指定权限的组织
     *
     * @param permission 权限节点
     * @return
     */
    @GetMapping("/has-permission-organizations")
    public ResponseEntity<List<OrganizationBasicDTO>> getUserHasPermissionOrganizations(
            @RequestParam(value = "permission") String permission
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(organizationClient.getUserHasPermissionOrganizations(permission));
    }

    /**
     * 获取当前用户指定功能模块下的权限
     *
     * @param functions 模块功能标示
     * @return
     */
    @GetMapping("/function/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getEmployee(
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "functions") List<String> functions
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(authClient.getUserFunctionPermissions(identity.getId().toString(), OrganizationSessionContext.contextId, organizationId == null ? null : organizationId.toString(), functions));
    }

    /**
     * 修改员工信息
     */
    @PatchMapping("/info")
    public ResponseEntity<Object> modifyUserInfo(
            @Valid @RequestBody UpdateIdentityEmployeeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        EmployeeIdentity identity = sessionContext.getEmployee(command.getOrganizationId());
        command.setIdentity(identity);
        employeeCommandService.updateIdentityEmployee(command);

        return ResponseUtils.ok("修改员工信息成功").build();
    }

    /**
     * 切换公司
     *
     * @param organizationId
     * @return
     */
    @PostMapping("/switch-organization/{organizationId}")
    public ResponseEntity<AccessTokenDTO> switchOrganization(
            @PathVariable(value = "organizationId") Long organizationId
    ) {
        Identity identity = sessionContext.getUser();
        Client client = sessionContext.getClient();
        MemberIdentity memberIdentity = memberSystemSessionContext.getMember();
        SwitchOrganizationCommand command = new SwitchOrganizationCommand();
        command.setClientId(client.getClientId());
        command.setOrganizationId(organizationId);
        command.setSystemId(memberIdentity.getMemberSystemId());
        AccessTokenDTO accessTokenDTO = employeeCommandService.switchOrganization(command, identity);

        return ResponseUtils.ok().body(accessTokenDTO);
    }
}