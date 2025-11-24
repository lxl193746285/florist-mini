package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.config.OrganizationConfig;
import com.qy.organization.app.config.OrganizationMode;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.dto.CategoryPermissionMenuDTO;
import com.qy.rbac.api.dto.MenuBasicDTO;
import com.qy.rbac.api.dto.ModuleMenuBasicDTO;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单权限
 *
 * @author legendjw
 */
@RestController
@Api(tags = "菜单权限")
@RequestMapping(value = "/v4/organization")
public class OrganizationMenuController {
    private OrganizationSessionContext sessionContext;
    private MemberSystemSessionContext memberSystemSessionContext;
    private MenuClient menuClient;
    private OrganizationConfig organizationConfig;

    public OrganizationMenuController(OrganizationSessionContext sessionContext, MenuClient menuClient, OrganizationConfig organizationConfig, MemberSystemSessionContext memberSystemSessionContext) {
        this.sessionContext = sessionContext;
        this.menuClient = menuClient;
        this.organizationConfig = organizationConfig;
        this.memberSystemSessionContext = memberSystemSessionContext;
    }

    /**
     * 获取当前认证的员工可以访问的菜单
     *
     * @param organizationId 组织id
     */
    @ApiOperation("获取当前认证的员工可以访问的菜单")
    @GetMapping("/frontend-menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getFrontendMenus(
            @RequestParam(value = "organization_id", required = false) String organizationId
    ) {
        Identity identity = sessionContext.getUser();
        Client client = sessionContext.getClient();
        //此处判断如果是单公司模式，则始终只返回当前公司的菜单
//        if (organizationId == null && organizationConfig.organizationMode.equals(OrganizationMode.SINGLE.name())) {
        EmployeeIdentity employeeIdentity = sessionContext.getEmployee();
        organizationId = employeeIdentity.getOrganizationId().toString();
//        }
        return ResponseUtils.ok().body(menuClient.getUserFrontendMenus(identity.getId().toString(), OrganizationSessionContext.contextId, organizationId, client.getClientId()));
    }


    /**
     * 获取当前认证的会员可以访问的菜单
     *
     * @param systemId 会员系统id
     */
    @ApiOperation("获取当前认证的会员可以访问的菜单")
    @GetMapping("/member-frontend-menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getMemberFrontendMenus(
            @RequestParam(value = "system_id", required = false) String systemId
    ) {
        MemberIdentity identity = memberSystemSessionContext.getMember();
        Client client = memberSystemSessionContext.getClient();
        //此处判断如果是单公司模式，则始终只返回当前公司的菜单
        if (systemId == null) {
            systemId = identity.getMemberSystemId().toString();
        }
        return ResponseUtils.ok().body(menuClient.getUserFrontendMenus(identity.getAccountId().toString(), MemberSystemSessionContext.contextId, systemId, client.getClientId()));
    }

    /**
     * 获取当前认证的员工可以访问的应用菜单
     *
     * @param organizationId 组织id
     * @param appCode        应用标示，目前有manage：后台管理，app：手机应用
     * @param moduleCode     模块标示，默认是：default
     */
    @ApiOperation("获取当前认证的员工可以访问的应用菜单")
    @GetMapping("/app-menus")
    public ResponseEntity<List<MenuBasicDTO>> getAppMenus(
            @RequestParam(value = "organization_id", required = false) String organizationId,
            @RequestParam(value = "app_code") String appCode,
            @RequestParam(value = "module_code", required = false, defaultValue = "default") String moduleCode
    ) {
        Identity identity = sessionContext.getUser();
        //此处判断如果是单公司模式，则始终只返回当前公司的菜单
        if (organizationId == null && organizationConfig.organizationMode.equals(OrganizationMode.SINGLE.name())) {
            EmployeeIdentity employeeIdentity = sessionContext.getEmployee();
            organizationId = employeeIdentity.getOrganizationId().toString();
        }
        return ResponseUtils.ok().body(menuClient.getUserAppMenus(identity.getId().toString(), OrganizationSessionContext.contextId, organizationId, appCode, moduleCode));
    }

    /**
     * 获取当前认证的员工的授权菜单
     *
     * @param organizationId 组织id
     */
    @ApiOperation("获取当前认证的员工的授权菜单")
    @GetMapping("/authorized-menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getAuthorizedMenus(
            @RequestParam(value = "organization_id") String organizationId,
            @RequestParam(value = "member_system_id", required = false) String memberSystemId
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(menuClient.getUserAuthorizedMenus(identity.getId().toString(),
                OrganizationSessionContext.contextId, organizationId, memberSystemId));
    }

    /**
     * 获取当前认证的员工的授权菜单下的权限节点
     *
     * @param organizationId 组织id
     * @param menuId         菜单id
     * @return
     */
    @ApiOperation("获取当前认证的员工的授权菜单下的权限节点")
    @GetMapping("/authorized-menus/permissions")
    public ResponseEntity<List<CategoryPermissionMenuDTO>> getAuthorizedMenuPermissions(
            @RequestParam(value = "organization_id") String organizationId,
            @RequestParam(value = "menu_id") Long menuId
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(menuClient.getUserAuthorizedMenuPermissions(identity.getId().toString(), OrganizationSessionContext.contextId, organizationId, menuId));
    }
}