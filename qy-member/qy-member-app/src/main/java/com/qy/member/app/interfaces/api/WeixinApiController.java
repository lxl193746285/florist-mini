package com.qy.member.app.interfaces.api;

import com.qy.member.app.application.command.LoginByMiniCommand;
import com.qy.member.app.application.command.MiniQrcodeCommand;
import com.qy.member.app.application.dto.WeixinLoginDTO;
import com.qy.member.app.application.dto.WeixinMiniQrcodeDTO;
import com.qy.member.app.application.dto.WeixinPhoneDTO;
import com.qy.member.app.application.query.WeixinMiniUserPhoneQuery;
import com.qy.member.app.application.service.WeixinService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 微信服务
 *
 * @author wwd
 */
@RestController
@RequestMapping(value = "/v4/api/mbr/weixin")
public class WeixinApiController {

    private WeixinService weixinService;

    public WeixinApiController(WeixinService weixinService) {
        this.weixinService = weixinService;
    }


    /**
     * 获取小程用户授权手机号
     *
     * @return
     */
    @GetMapping("/mini/phone")
    public WeixinPhoneDTO getMiniPhone(
            @RequestParam("code") String code,
            @RequestParam("wx_app_id") String wxAppId
    ) {
        WeixinMiniUserPhoneQuery query = new WeixinMiniUserPhoneQuery();
        query.setCode(code);
        query.setWxAppId(wxAppId);
        return weixinService.getMiniWxUserPhone(query);
    }


    /**
     * 获取小程用户授权登录，未注册账号自动登录
     *
     * @param command
     * @return
     */
    @PostMapping("/mini/login")
    public WeixinLoginDTO getMiniInfo(
            @Valid @RequestBody LoginByMiniCommand command
    ) {
        return weixinService.miniLogin(command);
    }

    /**
     * 获取小程码
     *
     * @param command
     * @return
     */
    @PostMapping("/mini/qrcode")
    public WeixinMiniQrcodeDTO getMiniQrcode(
            @Valid @RequestBody MiniQrcodeCommand command
    ) {
        return weixinService.getMiniQrcode(command);
    }
}
