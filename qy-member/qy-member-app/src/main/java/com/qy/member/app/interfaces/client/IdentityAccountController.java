package com.qy.member.app.interfaces.client;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.AccountBasicDTO;
import com.qy.member.app.application.dto.BoolResultDTO;
import com.qy.member.app.application.dto.MemberOrganizationBasicDTO;
import com.qy.member.app.application.service.AccountCommandService;
import com.qy.member.app.application.service.AccountQueryService;
import com.qy.member.app.application.service.AccountWeixinService;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Client;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 认证会员账号
 *
 * @author legendjw
 */
@RestController
@Api(tags = "认证会员账号")
@RequestMapping(value = "/v4/mbr/account")
public class IdentityAccountController {
    private MemberSystemSessionContext sessionContext;
    private AccountCommandService accountCommandService;
    private AccountQueryService accountQueryService;
    private AccountWeixinService accountWeixinService;
    private AuthClient authClient;
    private HttpServletRequest request;

    public IdentityAccountController(MemberSystemSessionContext sessionContext, AccountCommandService accountCommandService, AccountQueryService accountQueryService, AccountWeixinService accountWeixinService, AuthClient authClient) {
        this.sessionContext = sessionContext;
        this.accountCommandService = accountCommandService;
        this.accountQueryService = accountQueryService;
        this.accountWeixinService = accountWeixinService;
        this.authClient = authClient;
    }

    /**
     * 获取当前登录的账号
     */
    @GetMapping
    @ApiOperation("获取当前登录的账号")
    public ResponseEntity<AccountBasicDTO> getAccount() {
        MemberIdentity identity = sessionContext.getMember();
        return ResponseUtils.ok().body(accountQueryService.getBasicAccount(identity.getAccountId()));
    }

    /**
     * 获取当前登录的账号权限
     */
    @GetMapping("/permissions")
    @ApiOperation("获取当前登录的账号权限")
    public ResponseEntity<List<PermissionWithRuleDTO>> getAccountPermissions() {
        MemberIdentity identity = sessionContext.getMember();
        return ResponseUtils.ok().body(authClient.getUserPermissions(identity.getAccountId().toString(), MemberSystemSessionContext.contextId, identity.getMemberSystemId().toString()));
    }

    /**
     * 切换会员系统
     *
     * @param command
     */
    @PostMapping("/switch-member-system")
    @ApiOperation("切换会员系统")
    public ResponseEntity<AccessTokenDTO> switchMemberSystem(
            @Valid @RequestBody SwitchMemberSystemCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        MemberIdentity identity = sessionContext.getMember();
        Client client = sessionContext.getClient();
        command.setClientId(client.getClientId());
        command.setAccountId(identity.getAccountId());
        AccessTokenDTO accessTokenDTO = accountCommandService.switchMemberSystem(command);
        return ResponseUtils.created().body(accessTokenDTO);
    }

    /**
     * 修改账号信息
     */
    @PatchMapping("/info")
    @ApiOperation("修改账号信息")
    public ResponseEntity<Object> modifyAccountInfo(
            @Valid @RequestBody ModifyAccountInfoCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        accountCommandService.modifyAccountInfo(command, identity);

        return ResponseUtils.ok("修改账号信息成功").build();
    }


    /**
     * 获取已有的组织
     *
     */
    @ApiOperation("获取已有的组织")
    @GetMapping("/get-organization")
    public ResponseEntity<List<MemberOrganizationBasicDTO>> getOrganizationByPassword(
    ) {
        List<MemberOrganizationBasicDTO> memberSystemBasicList = accountCommandService.getOrganizations(sessionContext.getMember());
        return ResponseUtils.ok("登录成功").body(memberSystemBasicList);
    }

    /**
     * 更换手机号
     */
    @PatchMapping("/phone")
    @ApiOperation("更换手机号")
    public ResponseEntity<Object> changePhone(
            @Valid @RequestBody ChangePhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        accountCommandService.changeAccountPhone(command, identity);

        return ResponseUtils.ok("更换手机号成功").build();
    }

    /**
     * 通过手机号修改密码
     */
    @PatchMapping("/password-by-phone")
    @ApiOperation("通过手机号修改密码")
    public ResponseEntity<Object> modifyPasswordByPhone(
            @Valid @RequestBody ModifyPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        accountCommandService.modifyAccountPasswordByPhone(command, identity);

        return ResponseUtils.ok("密码修改成功").build();
    }

    /**
     * 通过密码修改密码
     */
    @PatchMapping("/password-by-password")
    @ApiOperation("通过密码修改密码")
    public ResponseEntity<Object> modifyPasswordByPassword(
            @Valid @RequestBody ModifyPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        accountCommandService.modifyPasswordByPassword(command, identity);

        return ResponseUtils.ok("密码修改成功").build();
    }


    /**
     * 修改账号手机id
     */
    @PatchMapping("/mobile-id")
    @ApiOperation("修改账号手机id")
    public ResponseEntity<Object> modifyAccountMobileId(
            @Valid @RequestBody ModifyAccountMobileIdCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        accountCommandService.modifyAccountMobileId(command, identity);

        return ResponseUtils.ok().build();
    }

    /**
     * 是否绑定微信
     *
     * @param appId 微信应用id
     */
    @GetMapping("/is-bind-weixin")
    @ApiOperation("是否绑定微信")
    public ResponseEntity<BoolResultDTO> getIsBindWeixin(
            @RequestParam(value = "app_id") String appId
    ) {
        MemberIdentity identity = sessionContext.getMember();
        return ResponseUtils.ok().body(new BoolResultDTO(accountWeixinService.isBindWeixin(appId, identity.getAccountId(), identity.getOrganizationId())));
    }

    /**
     * 绑定微信
     */
    @PostMapping("/bind-weixin")
    @ApiOperation("绑定微信")
    public ResponseEntity<Object> bindWeixin(
            @Valid @RequestBody BindWeixinCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        accountWeixinService.bindWeixin(command, identity);
        return ResponseUtils.ok("绑定微信成功").build();
    }

    /**
     * 解绑微信
     */
    @PostMapping("/unbind-weixin")
    @ApiOperation("解绑微信")
    public ResponseEntity<Object> unbindWeixin(
            @Valid @RequestBody BindWeixinCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        command.setClientId(sessionContext.getClient().getClientId());
        accountWeixinService.unbindWeixin(command, identity);
        return ResponseUtils.ok("解绑微信成功").build();
    }

    /**
     * 退出登录
     *
     * @param command
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseEntity<Object> loginByPhone(
            @Valid @RequestBody LogoutCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        MemberIdentity identity = sessionContext.getMember();
        command.setUserId(identity.getAccountId());
        command.setClientId(sessionContext.getClient().getClientId());
        command.setMemberId(identity.getId());
        command.setOrganizationId(identity.getOrganizationId());
        accountCommandService.logout(command);
        return ResponseUtils.ok("退出成功").build();
    }

    /**
     * 登录设备验证
     *
     * @param command
     */
    @ApiOperation("登录设备验证")
    @PostMapping("/verify-device")
    public ResponseEntity<Object> verifyDevice(
            @Valid @RequestBody VerifyDeviceCommand command,
            BindingResult result
    ) {
        try {
            accountCommandService.verifyDevice(command, sessionContext.getMember());
        }catch (Exception e){
            if (e.getMessage().contains("登录设备非绑定设备，需短信验证登录")){
                return ResponseUtils.status(HttpStatus.UNPROCESSABLE_ENTITY, "登录设备非绑定设备，需短信验证登录").build();
            }
        }
        return ResponseUtils.ok("验证成功").build();
    }

}