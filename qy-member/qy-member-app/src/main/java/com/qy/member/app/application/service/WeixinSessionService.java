package com.qy.member.app.application.service;

import com.qy.member.app.application.command.CreateWeixinSessionCommand;
import com.qy.member.app.application.dto.WeixinSessionDTO;
import com.qy.member.app.domain.valueobject.WxAuthUser;

/**
 * 微信会话服务
 *
 * @author legendjw
 */
public interface WeixinSessionService {
    /**
     * 根据微信token获取微信会话
     *
     * @param weixinToken
     * @return
     */
    WeixinSessionDTO getWeixinSessionByWeixinToken(String weixinToken);

    /**
     * 根据微信token获取微信会话
     *
     * @param id
     * @return
     */
    WeixinSessionDTO getWeixinSessionById(Long id);

    /**
     * 创建微信会话
     *
     * @param command
     * @return
     */
    Long createWeixinSession(CreateWeixinSessionCommand command);

    /**
     * 创建微信会话
     *
     * @param appId
     * @param wxOAuth2UserInfo
     * @return
     */
    WeixinSessionDTO createWeixinSession(String appId, WxAuthUser wxOAuth2UserInfo);
}