package com.qy.member.api.client;

import com.qy.member.api.command.LoginByPasswordCommand;
import com.qy.member.api.command.LoginByWeixinCommand;
import com.qy.member.api.command.ModifyPasswordCommand;
import com.qy.member.api.command.UpdatePhoneCommand;
import com.qy.member.api.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员账号客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-member", contextId = "qy-member-account")
public interface AccountClient {
    /**
     * 根据id获取基本会员信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/basic-accounts/{id}")
    AccountBasicDTO getBasicAccount(
            @PathVariable(value = "id") Long id
    );

    /**
     * 根据id获取基本会员信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/basic-accounts/phone")
    AccountBasicDTO getBasicAccountByPhone(
            @RequestParam(value = "phone") String phone
    );

    /**
     * 获取指定会员的主账号
     *
     * @param memberId
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/{memberId}/primary-account")
    AccountBasicDTO getPrimaryAccount(
            @PathVariable(value = "memberId") Long memberId
    );

    /**
     * 获取指定会员的所有子账号
     *
     * @param memberId
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/{memberId}/child-accounts")
    List<AccountBasicDTO> getChildAccounts(
            @PathVariable(value = "memberId") Long memberId
    );

    /**
     * 获取发送消息会员账号信息
     *
     * @param memberId
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/{memberId}/send-message-account-info")
    SendMessageAccountInfoDTO getSendMessageAccountInfo(
            @PathVariable(value = "memberId") Long memberId
    );

    /**
     * 获取指定微信应用下账号的绑定微信信息
     *
     * @param appId 微信应用id
     * @param accountId 会员账号id
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/bind-weixin-info")
    BindWeixinInfoDTO getAccountBindWeixinInfo(
            @RequestParam(value = "app_id") String appId,
            @RequestParam(value = "account_id") Long accountId
    );

    /**
     * 获取指定支付方式下会员的绑定微信信息
     *
     * @param subPayWay 支付方式
     * @param memberId 会员id
     * @return
     */
    @GetMapping("/v4/api/mbr/accounts/bind-weixin-info-by-pay-way")
    BindWeixinInfoDTO getAccountBindWeixinInfoByPayWay(
            @RequestParam(value = "sub_pay_way") String subPayWay,
            @RequestParam(value = "member_id") Long memberId
    );

    /**
     * 修改会员账号手机号
     *
     * @param accountId
     * @return
     */
    @PostMapping("/v4/api/mbr/accounts/{accountId}/account-update-phone")
    ResponseEntity updateAccountPhone(
            @PathVariable(value = "accountId") Long accountId,
            @RequestBody @Valid UpdatePhoneCommand command);

    /**
     * 修改账号密码
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/mbr/accounts/{accountId}/modify-password")
    void modifyPassword(
            @PathVariable(value = "accountId") Long id,
            @Valid @RequestBody ModifyPasswordCommand command
    );

    @PatchMapping("/v4/api/mbr/accounts/byPhone/modify-password")
    void modifyPasswordByPhoneAndVerificationCode(
            @Valid @RequestBody ModifyPasswordCommand command
    );

    @PostMapping("/v4/api/mbr/accounts/login-by-password")
    AccessTokenDTO loginByPassword(
            @Valid @RequestBody LoginByPasswordCommand command
    );

    @PostMapping("/v4/api/mbr/accounts/login-by-weixin")
    WeixinLoginDTO loginByWeixin(
            @Valid @RequestBody LoginByWeixinCommand command
    );

    /**
     * 账号验证
     *
     * @param username
     * @param password
     * @param systemId
     * @param organizationId
     */
    @PostMapping("/v4/api/mbr/accounts/valid-account")
    MemberBasicDTO validAccount(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "systemId") Long systemId,
            @RequestParam(value = "organization_id") Long organizationId
    );
}