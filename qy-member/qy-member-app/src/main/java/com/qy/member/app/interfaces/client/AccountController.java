package com.qy.member.app.interfaces.client;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.service.AccountCommandService;
import com.qy.member.app.application.service.AccountQueryService;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.application.service.MemberQueryService;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.MemberSystemSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 会员账号
 *
 * @author legendjw
 */
@RestController
@Api(tags = "会员账号")
@RequestMapping(value = "/v4/mbr/accounts")
public class AccountController {
    private AccountCommandService accountCommandService;
    private AccountQueryService accountQueryService;
    private MemberQueryService memberQueryService;
    private MemberCommandService memberCommandService;
    private MemberSystemSessionContext sessionContext;

    public AccountController(AccountCommandService accountCommandService, AccountQueryService accountQueryService, MemberQueryService memberQueryService,
                             MemberCommandService memberCommandService, MemberSystemSessionContext sessionContext) {
        this.accountCommandService = accountCommandService;
        this.accountQueryService = accountQueryService;
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
        this.sessionContext = sessionContext;
    }

    /**
     * 指定手机号是否已经注册账号
     *
     * @param phone
     */
    @ApiOperation("指定手机号是否已经注册账号")
    @GetMapping("/is-phone-registered")
    public ResponseEntity<IsPhoneRegisteredDTO> isPhoneRegistered(
            @RequestParam(value = "phone") String phone
    ) {
        return ResponseUtils.ok().body(accountQueryService.isPhoneRegistered(phone));
    }

    /**
     * 注册账号
     *
     * @param command
     */
    @ApiOperation("注册账号")
    @PostMapping
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdDTO memberId = memberCommandService.registerMember(command);
        MemberDTO memberDTO = memberQueryService.getMember(memberId.getId());
        if (memberDTO.getAuditStatusId().intValue() == AuditStatus.PLATFORM_PASSED.getId()) {
            return ResponseUtils.created("注册会员成功").build();
        } else {
            return ResponseUtils.created("申请注册会员成功，请等待审核").build();
        }
    }

    /**
     * 密码登录
     *
     * @param command
     */
    @ApiOperation("密码登录")
    @PostMapping("/login-by-password")
    public ResponseEntity<AccessTokenDTO> loginByPassword(
            @Valid @RequestBody LoginByPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        AccessTokenDTO accessTokenDTO = accountCommandService.loginByPassword(command);
        return ResponseUtils.created("登录成功").body(accessTokenDTO);
    }

    /**
     * 短信验证码登录
     *
     * @param command
     */
    @ApiOperation("短信验证码登录")
    @PostMapping("/login-by-phone")
    public ResponseEntity<AccessTokenDTO> loginByPhone(
            @Valid @RequestBody LoginByPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        AccessTokenDTO accessTokenDTO = accountCommandService.loginByPhone(command);
        return ResponseUtils.created("登录成功").body(accessTokenDTO);
    }

    /**
     * 微信登录
     *
     * @param command
     */
    @ApiOperation("微信登录")
    @PostMapping("/login-by-weixin")
    public ResponseEntity<WeixinLoginDTO> loginByWeixin(
            @Valid @RequestBody LoginByWeixinCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        WeixinLoginDTO weixinLoginDTO = accountCommandService.loginByWeixin(command);
        return ResponseUtils.created("登录成功").body(weixinLoginDTO);
    }


    /**
     * 刷新令牌
     *
     * @param command
     */
    @ApiOperation("刷新令牌")
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
    @ApiOperation("忘记密码通过手机号找回密码")
    @PostMapping("/retrieve-password-by-phone")
    public ResponseEntity<Object> retrievePasswordByPhone(
            @Valid @RequestBody RetrievePasswordByPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        accountCommandService.retrievePasswordByPhone(command);
        return ResponseUtils.ok("重置密码成功").build();
    }

    /**
     * 密码获取已有的会员系统
     *
     * @param command
     */
    @ApiOperation("密码获取已有的会员系统")
    @PostMapping("/get-systems-by-password")
    public ResponseEntity<List<MemberSystemBasicDTO>> getSystemsByPassword(
            @Valid @RequestBody LoginByPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        List<MemberSystemBasicDTO> memberSystemBasicList = accountCommandService.getSystemsByPassword(command);
        return ResponseUtils.ok("登录成功").body(memberSystemBasicList);
    }

    /**
     * 手机号验证码获取已有的会员系统
     *
     * @param command
     */
    @ApiOperation("手机号验证码获取已有的会员系统")
    @PostMapping("/get-systems-by-phone")
    public ResponseEntity<List<MemberSystemBasicDTO>> getSystemsByPhone(
            @Valid @RequestBody LoginByPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        List<MemberSystemBasicDTO> memberSystemBasicList = accountCommandService.getSystemsByPhone(command);
        return ResponseUtils.ok("登录成功").body(memberSystemBasicList);
    }


}