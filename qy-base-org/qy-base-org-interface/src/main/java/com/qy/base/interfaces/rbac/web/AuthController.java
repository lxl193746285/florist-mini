package com.qy.base.interfaces.rbac.web;

import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import com.qy.rbac.app.application.service.AuthQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 授权服务
 *
 * @author hh
 */
@Api(tags = "授权服务")
@RestController
@RequestMapping("/v4/rbac/auth")
public class AuthController  {
    private OrganizationSessionContext sessionContext;
    private AuthQueryService authQueryService;

    public AuthController(OrganizationSessionContext sessionContext, AuthQueryService authQueryService) {
        this.sessionContext = sessionContext;
        this.authQueryService = authQueryService;
    }
    /**
     * 获取当前用户在指定上下文的指定功能模块的权限
     *
     * @param context
     * @param contextId
     * @param functions
     * @return
     */
    @ApiOperation("获取当前用户在指定上下文的指定功能模块的权限")
    @GetMapping("/user/context/function/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getUserPermissions(
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "functions") List<String> functions
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(authQueryService.getUserFunctionPermissions(identity.getId().toString(), context, contextId, functions));
    }

    /**
     * 获取拥有指定权限的用户
     *
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    @ApiOperation("获取拥有指定权限的用户")
    @GetMapping("/has-permission-users")
    public ResponseEntity<List<String>> getHasPermissionUsers(
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId,
            @RequestParam(value = "permission") String permission
    ) {
        return ResponseUtils.ok().body(authQueryService.getHasPermissionUsers(context, contextId, permission));
    }

}
