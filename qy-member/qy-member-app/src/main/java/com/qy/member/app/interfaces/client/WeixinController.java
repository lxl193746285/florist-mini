package com.qy.member.app.interfaces.client;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.member.app.application.command.CreateWeixinSessionCommand;
import com.qy.member.app.application.dto.WeixinPhoneDTO;
import com.qy.member.app.application.dto.WeixinSessionDTO;
import com.qy.member.app.application.query.WeixinAppAuthorizationUrlQuery;
import com.qy.member.app.application.query.WeixinAppJsapiQuery;
import com.qy.member.app.application.query.WeixinMiniUserPhoneQuery;
import com.qy.member.app.application.service.MemberSystemWeixinAppQueryService;
import com.qy.member.app.application.service.WeixinService;
import com.qy.member.app.application.service.WeixinSessionService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 微信服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/mbr/weixin")
public class WeixinController {
    private WeixinService weixinService;
    private WeixinSessionService weixinSessionService;
    private MemberSystemWeixinAppQueryService weixinAppQueryService;

    public WeixinController(WeixinService weixinService, WeixinSessionService weixinSessionService, MemberSystemWeixinAppQueryService weixinAppQueryService) {
        this.weixinService = weixinService;
        this.weixinSessionService = weixinSessionService;
        this.weixinAppQueryService = weixinAppQueryService;
    }

    /**
     * 获取微信授权获取用户信息地址
     *
     * @param query
     * @return
     */
    @GetMapping("/authorization-url")
    @ApiOperation(value = "获取微信授权获取用户信息地址")
    public ResponseEntity<String> getAuthorizationUrl(
            WeixinAppAuthorizationUrlQuery query
    ) {
        return ResponseUtils.ok().body(weixinService.getAuthorizationUrl(query));
    }

    /**
     * 获取微信jsapi
     *
     * @param query
     * @return
     */
    @GetMapping("/jsapi-signature")
    @ApiOperation(value = "获取微信jsapi")
    public ResponseEntity<WxJsapiSignature> getJsapiSignature(
            WeixinAppJsapiQuery query
    ) {
        return ResponseUtils.ok().body(weixinService.getJsapiSignature(query));
    }

    /**
     * 创建微信授权会话
     *
     * @param command
     */
    @PostMapping("/session")
    @ApiOperation(value = "创建微信授权会话")
    public ResponseEntity<WeixinSessionDTO> createWeixinSession(
            @Valid @RequestBody CreateWeixinSessionCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Long sessionId = weixinSessionService.createWeixinSession(command);
        return ResponseUtils.created().body(weixinSessionService.getWeixinSessionById(sessionId));
    }

    /**
     * 获取微信应用二维码图片
     *
     * @param systemId
     * @param appId
     * @return
     */
    @GetMapping("/app-qr-code")
    public ResponseEntity<AttachmentBasicDTO> getQrcode(
            @RequestParam(value = "system_id") String systemId,
            @RequestParam(value = "app_id") String appId
    ) {
        return ResponseUtils.ok().body(weixinAppQueryService.getMemberSystemWeixinAppQrCode(Long.valueOf(systemId), appId));
    }

    /**
     * 获取小程用户授权手机号
     *
     * @param query
     * @return
     */
    @GetMapping("/mini/phone")
    public ResponseEntity<WeixinPhoneDTO> getMiniPhone(
            WeixinMiniUserPhoneQuery query
    ) {
        return ResponseUtils.ok().body(weixinService.getMiniWxUserPhone(query));
    }
}