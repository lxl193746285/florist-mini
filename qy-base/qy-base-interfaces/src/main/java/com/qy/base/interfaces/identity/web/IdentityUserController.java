package com.qy.base.interfaces.identity.web;

import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.dto.UserBasicDTO;
import com.qy.identity.app.application.service.AccountCommandService;
import com.qy.identity.app.application.service.UserCommandService;
import com.qy.identity.app.application.service.UserQueryService;
import com.qy.rbac.api.client.MenuClient;
import com.qy.rbac.api.dto.ModuleMenuBasicDTO;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Client;
import com.qy.security.session.Identity;
import com.qy.security.session.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 认证用户
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/identity/user")
public class IdentityUserController {
    private SessionContext sessionContext;
    private UserCommandService userCommandService;
    private UserQueryService userQueryService;
    private AccountCommandService accountCommandService;
    private MenuClient menuClient;
    private HttpServletRequest request;

    public IdentityUserController(SessionContext sessionContext, UserCommandService userCommandService, UserQueryService userQueryService, AccountCommandService accountCommandService, MenuClient menuClient, HttpServletRequest request) {
        this.sessionContext = sessionContext;
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.accountCommandService = accountCommandService;
        this.menuClient = menuClient;
        this.request = request;
    }

    /**
     * 获取当前登录的用户
     */
    @GetMapping
    public ResponseEntity<UserBasicDTO> getUser() {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(userQueryService.getBasicUserById(identity.getId()));
    }

    /**
     * 获取当前登录的用户可以访问的菜单
     */
    @GetMapping("/menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getMenus() {
        Identity identity = sessionContext.getUser();
        Client client = sessionContext.getClient();
        return ResponseUtils.ok().body(menuClient.getUserFrontendMenus(identity.getId().toString(), client.getClientId()));
    }

    /**
     * 修改用户信息
     */
    @PatchMapping("/info")
    public ResponseEntity<Object> modifyUserInfo(
            @Valid @RequestBody ModifyUserInfoCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Identity identity = sessionContext.getUser();
        command.setUserId(identity.getId());
        userCommandService.modifyUserInfo(command);

        return ResponseUtils.ok("修改用户信息成功").build();
    }

    /**
     * 更换手机号
     */
    @PatchMapping("/phone")
    public ResponseEntity<Object> changePhone(
            @Valid @RequestBody ChangePhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Identity identity = sessionContext.getUser();
        command.setUserId(identity.getId());
        userCommandService.changeUserPhone(command);

        return ResponseUtils.ok("更换手机号成功").build();
    }

    /**
     * 更换用户名
     */
    @PatchMapping("/username")
    public ResponseEntity<Object> changeUsername(
            @Valid @RequestBody ChangeUsernameCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Identity identity = sessionContext.getUser();
        command.setUserId(identity.getId());
        userCommandService.changeUsername(command);

        return ResponseUtils.ok("更换用户名成功").build();
    }

    /**
     * 通过手机号修改密码
     */
    @PatchMapping("/password-by-phone")
    public ResponseEntity<Object> modifyPasswordByPhone(
            @Valid @RequestBody ModifyPasswordByPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Identity identity = sessionContext.getUser();
        command.setUserId(identity.getId());
        userCommandService.modifyUserPasswordByPhone(command);

        return ResponseUtils.ok("密码修改成功").build();
    }

    /**
     * 通过旧密码修改密码
     */
    @PatchMapping("/password-by-old-password")
    public ResponseEntity<Object> modifyUserPasswordByOldPassword(
            @Valid @RequestBody ModifyPasswordByOldPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Identity identity = sessionContext.getUser();
        command.setUserId(identity.getId());
        userCommandService.modifyUserPasswordByOldPassword(command);

        return ResponseUtils.ok("密码修改成功").build();
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        Identity identity = sessionContext.getUser();
        Client client = sessionContext.getClient();
        LogoutCommand command = new LogoutCommand();
        command.setUserId(identity.getId());
        command.setClientId(client.getClientId());
        accountCommandService.logout(command, request);
        return ResponseUtils.ok("退出登录成功").build();
    }
}