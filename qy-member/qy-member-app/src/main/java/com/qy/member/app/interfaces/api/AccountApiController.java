package com.qy.member.app.interfaces.api;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.member.app.application.command.LoginByPasswordCommand;
import com.qy.member.app.application.command.LoginByWeixinCommand;
import com.qy.member.app.application.command.ModifyPasswordCommand;
import com.qy.member.app.application.command.UpdatePhoneCommand;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.service.AccountCommandService;
import com.qy.member.app.application.service.AccountQueryService;
import com.qy.member.app.application.service.AccountWeixinService;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员账号内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/api/mbr/accounts")
public class AccountApiController {
    private AccountQueryService accountQueryService;
    private AccountCommandService accountCommandService;
    private AccountWeixinService accountWeixinService;

    public AccountApiController(AccountQueryService accountQueryService, AccountCommandService accountCommandService, AccountWeixinService accountWeixinService) {
        this.accountQueryService = accountQueryService;
        this.accountCommandService = accountCommandService;
        this.accountWeixinService = accountWeixinService;
    }

    /**
     * 根据id获取基本账号信息
     *
     * @param id
     * @return
     */
    @GetMapping("/basic-accounts/{id}")
    public ResponseEntity<AccountBasicDTO> getBasicAccount(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(accountQueryService.getBasicAccount(id));
    }

    /**
     * 获取指定会员的主账号
     *
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}/primary-account")
    public ResponseEntity<AccountBasicDTO> getPrimaryAccount(
            @PathVariable(value = "memberId") Long memberId
    ) {
        return ResponseUtils.ok().body(accountQueryService.getPrimaryAccount(memberId));
    }

    /**
     * 获取指定会员的所有子账号
     *
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}/child-accounts")
    public ResponseEntity<List<AccountBasicDTO>> getChildAccounts(
            @PathVariable(value = "memberId") Long memberId
    ) {
        return ResponseUtils.ok().body(accountQueryService.getChildAccounts(memberId));
    }

    /**
     * 获取发送消息会员账号信息
     *
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}/send-message-account-info")
    public ResponseEntity<SendMessageAccountInfoDTO> getSendMessageAccountInfo(
            @PathVariable(value = "memberId") Long memberId
    ) {
        return ResponseUtils.ok().body(accountQueryService.getSendMessageAccountInfo(memberId));
    }

    /**
     * 获取指定微信应用下账号的绑定微信信息
     *
     * @param appId 微信应用id
     * @return
     */
    @GetMapping("/bind-weixin-info")
    public ResponseEntity<BindWeixinInfoDTO> getAccountBindWeixinInfo(
            @RequestParam(value = "app_id") String appId,
            @RequestParam(value = "account_id") Long accountId
    ) {
        return ResponseUtils.ok().body(accountWeixinService.getAccountBindWeixinInfo(appId, accountId));
    }

    /**
     * 获取指定支付方式下会员的绑定微信信息
     *
     * @param subPayWay 支付方式
     * @param memberId 会员id
     * @return
     */
    @GetMapping("/bind-weixin-info-by-pay-way")
    public ResponseEntity<BindWeixinInfoDTO> getAccountBindWeixinInfoByPayWay(
            @RequestParam(value = "sub_pay_way") String subPayWay,
            @RequestParam(value = "member_id") Long memberId
    ) {
        return ResponseUtils.ok().body(accountWeixinService.getAccountBindWeixinInfoByPayWay(subPayWay, memberId));
    }

    /**
     * 修改会员账号手机号
     *
     * @param command
     */
    @PostMapping("{accountId}/account-update-phone")
    public ResponseEntity updateAccountPhone(
            @PathVariable(value = "accountId") Long accountId,
            @RequestBody @Valid UpdatePhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        accountCommandService.updateAccountPhone(command,accountId);
        return ResponseUtils.ok("修改会员手机号成功").build();
    }

    /**
     * 修改账号密码
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{accountId}/modify-password")
    public ResponseEntity modifyPassword(
            @PathVariable(value = "accountId") Long id,
            @Valid @RequestBody ModifyPasswordCommand command
    ){
        accountCommandService.modifyAccountPassword(command,id);
        return ResponseUtils.ok("修改会员手机号成功").build();
    }

    /**
     *  通过验证码手机号修改密码
     * @param command
     * @return
     */
    @PatchMapping("/byPhone/modify-password")
    public ResponseEntity modifyPasswordByPhoneAndVerificationCode(
            @Valid @RequestBody com.qy.member.api.command.ModifyPasswordCommand command
    ){
        accountCommandService.modifyPasswordByPhoneAndVerificationCode(command);
        return ResponseUtils.ok("修改会员手机号成功").build();
    }


    /**
     * 根据手机号获取基本账号信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/basic-accounts/phone")
    public ResponseEntity<AccountBasicDTO> getBasicAccountByPhone(
            @RequestParam(value = "phone") String phone
    ) {
        return ResponseUtils.ok().body(accountQueryService.getAccountByPhone(phone));
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

        AccessTokenDTO accessTokenDTO = accountCommandService.loginByPassword(command);
        return ResponseUtils.created("登录成功").body(accessTokenDTO);
    }

    /**
     * 微信登录
     *
     * @param command
     */
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
     * 账号验证
     *
     * @param username
     * @param password
     * @param systemId
     * @param organizationId
     */
    @PostMapping("/valid-account")
    public MemberBasicDTO validAccount(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "systemId") Long systemId,
            @RequestParam(value = "organization_id") Long organizationId
    ) {

        MemberBasicDTO member = accountCommandService.validAccount(username,password,systemId,organizationId);

        return member;
    }
}