package com.qy.member.api.client;

import com.qy.member.api.command.LoginByMiniCommand;
import com.qy.member.api.command.MiniQrcodeCommand;
import com.qy.member.api.dto.WeixinLoginDTO;
import com.qy.member.api.dto.WeixinMiniQrcodeDTO;
import com.qy.member.api.dto.WeixinPhoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 微信客户端
 *
 * @author wwd
 */
@FeignClient(name = "qy-member", contextId = "qy-member-weixin")
public interface WeixinClient {

    /**
     * 获取微信小程序授权手机号
     *@ return
     */
    @GetMapping("/v4/api/mbr/weixin/mini/phone")
    WeixinPhoneDTO getMiniUserPhone(
            @RequestParam("code") String code,
            @RequestParam("wx_app_id") String wxAppId
    );
    /**
     * 获取微信小程序用户授权登录信息
     * @param command
     *@ return
     */
    @PostMapping("/v4/api/mbr/weixin/mini/login")
    WeixinLoginDTO loginByWeixin(
            @Valid @RequestBody LoginByMiniCommand command
    );

    /**
     * 获取微信小程序码
     * @param command
     *@ return
     */
    @PostMapping("/v4/api/mbr/weixin/mini/qrcode")
    WeixinMiniQrcodeDTO getMiniQrcode(
            @Valid @RequestBody MiniQrcodeCommand command
    );
}