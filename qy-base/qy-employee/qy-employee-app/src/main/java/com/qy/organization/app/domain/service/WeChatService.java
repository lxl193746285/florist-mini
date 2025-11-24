package com.qy.organization.app.domain.service;

import com.qy.organization.app.domain.entity.ResDto;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface WeChatService {

    /**
     * 获取微信服务
     *
     * @return
     */
    WxMpService getWxMpService();

    /**
     * 获取用户信息
     *
     * @param code
     * @return WxMpUser
     */
    WxMpUser getWxMpUser(String code);

    ResDto getUserInfo(String code);

}
