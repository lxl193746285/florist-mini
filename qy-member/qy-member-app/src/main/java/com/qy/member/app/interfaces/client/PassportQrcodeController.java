package com.qy.member.app.interfaces.client;

import com.qy.member.app.application.command.SystemPassportQrcodeCommand;
import com.qy.member.app.application.dto.SystemPassportQrcodeDTO;
import com.qy.member.app.application.service.SystemPassportQrcodeService;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.util.QrcodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 二维码
 *
 * @author wwd
 */
@RestController
@Api(tags = "二维码")
@RequestMapping(value = "/v4/mbr/passport-qrcode")
public class PassportQrcodeController {
    private SystemPassportQrcodeService systemPassportQrcodeService;
    private MemberSystemSessionContext memberSystemSessionContext;

    public PassportQrcodeController(SystemPassportQrcodeService systemPassportQrcodeService
            , MemberSystemSessionContext memberSystemSessionContext) {
        this.systemPassportQrcodeService = systemPassportQrcodeService;
        this.memberSystemSessionContext = memberSystemSessionContext;
    }

    /**
     * 获取登录二维码
     * @param uuid
     * @param linkType
     * @param clientId
     * @param response
     */
    @ApiOperation(value = "获取登录二维码")
    @GetMapping(value = "/login")
    public void getLoginPassport(
            @RequestParam(name = "uuid") String uuid,
            @RequestParam(name = "linkType") String linkType,
            @RequestParam(name = "clientId") String clientId,
            HttpServletResponse response) {
        try {
            String url = systemPassportQrcodeService.getLoginPassport(uuid, linkType, clientId);
            QrcodeUtils.qrcode(response, url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取微信登录二维码
     * @param uuid
     * @param linkType
     * @param clientId
     * @param path
     * @param response
     */
    @ApiOperation(value = "获取微信登录二维码")
    @GetMapping(value = "/weixin")
    public void getWeixinPassport(
            @RequestParam(name = "uuid") String uuid,
            @RequestParam(name = "linkType") String linkType,
            @RequestParam(name = "clientId") String clientId,
            @RequestParam(name = "path") String path,
            HttpServletResponse response) {
        try {
            String url = systemPassportQrcodeService.getWeixinPassport(uuid, linkType, clientId, path);
            QrcodeUtils.qrcode(response, url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 扫码操作
     * @param command
     * @param uuid
     * @param request
     */
    @ApiOperation(value = "扫码操作")
    @PostMapping(value = "/action/{uuid}")
    public void actionPassport(
            @RequestBody @Valid SystemPassportQrcodeCommand command,
            @PathVariable(name = "uuid") String uuid,
            HttpServletRequest request) {
        systemPassportQrcodeService.action(uuid, command.getStatus(), request, memberSystemSessionContext.getMember());
    }

    /**
     * 二维码轮询检测
     * @param uuid
     * @param type
     * @return
     */
    @ApiOperation(value = "二维码轮询检测")
    @GetMapping(value = "/check/{uuid}")
    public SystemPassportQrcodeDTO checkPassport(
            @PathVariable(name = "uuid") String uuid,
            @RequestParam(name = "link_type") String type) {
        return systemPassportQrcodeService.check(uuid, type);
    }
}