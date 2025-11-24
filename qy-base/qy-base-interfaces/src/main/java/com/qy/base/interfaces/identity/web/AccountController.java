package com.qy.base.interfaces.identity.web;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.service.AccountCommandService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 账号服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/identity/accounts")
public class AccountController {
    private AccountCommandService accountCommandService;
    private HttpServletRequest request;

    public AccountController(AccountCommandService accountCommandService, HttpServletRequest request) {
        this.accountCommandService = accountCommandService;
        this.request = request;
    }

    /**
     * 注册账号
     *
     * @param command
     */
    @PostMapping
    public ResponseEntity<AccessTokenDTO> register(
            @Valid @RequestBody RegisterAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        AccessTokenDTO accessTokenDTO = accountCommandService.registerAccount(command);
        return ResponseUtils.created("注册成功").body(accessTokenDTO);
    }

    /**
     * 密码登录
     *
     * @param command
     */
    @PostMapping("/login-by-password")
    public ResponseEntity<AccessTokenDTO> loginByPassword(
            @Valid @RequestBody LoginByPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        AccessTokenDTO accessTokenDTO = accountCommandService.loginByPassword(command, request);
        return ResponseUtils.created("登录成功").body(accessTokenDTO);
    }

    /**
     * 短信验证码登录
     *
     * @param command
     */
    @PostMapping("/login-by-phone")
    public ResponseEntity<AccessTokenDTO> loginByPhone(
            @Valid @RequestBody LoginByPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        AccessTokenDTO accessTokenDTO = accountCommandService.loginByPhone(command, request);
        return ResponseUtils.created("登录成功").body(accessTokenDTO);
    }

    /**
     * 刷新令牌
     *
     * @param command
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenDTO> refreshToken(
            @Valid @RequestBody RefreshTokenCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        AccessTokenDTO accessTokenDTO = accountCommandService.refreshToken(command);
        return ResponseUtils.created().body(accessTokenDTO);
    }

    /**
     * 忘记密码通过手机号找回密码
     *
     * @param command
     */
    @PostMapping("/retrieve-password-by-phone")
    public ResponseEntity<Object> retrievePasswordByPhone(
            @Valid @RequestBody RetrievePasswordByPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        accountCommandService.retrievePasswordByPhone(command);
        return ResponseUtils.ok("重置密码成功").build();
    }
}