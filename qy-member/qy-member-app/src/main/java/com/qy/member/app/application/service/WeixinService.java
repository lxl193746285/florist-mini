package com.qy.member.app.application.service;

import com.qy.member.app.application.command.LoginByMiniCommand;
import com.qy.member.app.application.command.MiniQrcodeCommand;
import com.qy.member.app.application.dto.WeixinLoginDTO;
import com.qy.member.app.application.dto.WeixinMiniQrcodeDTO;
import com.qy.member.app.application.dto.WeixinPhoneDTO;
import com.qy.member.app.application.query.*;
import com.qy.member.app.domain.valueobject.WxAuthUser;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.service.WxService;

/**
 * 微信服务
 *
 * @author legendjw
 */
public interface WeixinService {
    /**
     * 获取微信服务
     *
     * @param query
     * @return
     */
    WxService getWxService(SystemIdAndWeixinAppIdQuery query);

    /**
     * 获取微信授权地址
     *
     * @param query
     * @return
     */
    String getAuthorizationUrl(WeixinAppAuthorizationUrlQuery query);

    /**
     * 获取微信授权用户基本信息
     *
     * @param query
     * @return
     */
    WxAuthUser getWxUserInfo(WeixinAppUserQuery query);

    /**
     * 获取jsapi签名
     *
     * @param query
     * @return
     */
    WxJsapiSignature getJsapiSignature(WeixinAppJsapiQuery query);


    /**
     * 获取小程用户授权手机号
     * @param query
     *@ return
     */
    WeixinPhoneDTO getMiniWxUserPhone(WeixinMiniUserPhoneQuery query);

    WeixinLoginDTO miniLogin(LoginByMiniCommand command);

    WeixinMiniQrcodeDTO getMiniQrcode(MiniQrcodeCommand command);
}
