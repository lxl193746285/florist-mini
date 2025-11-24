package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.assembler.WeixinSessionAssembler;
import com.qy.member.app.application.command.CreateWeixinSessionCommand;
import com.qy.member.app.application.dto.WeixinSessionDTO;
import com.qy.member.app.application.query.WeixinAppUserQuery;
import com.qy.member.app.application.repository.WeixinSessionDataRepository;
import com.qy.member.app.application.service.WeixinService;
import com.qy.member.app.application.service.WeixinSessionService;
import com.qy.member.app.domain.valueobject.WxAuthUser;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.WeixinSessionDO;
import com.qy.rest.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 微信会话服务实现
 *
 * @author legendjw
 */
@Service
public class WeixinSessionServiceImpl implements WeixinSessionService {
    public static long sessionExpireTime = 7200;
    private WeixinService weixinService;
    private WeixinSessionDataRepository weixinSessionDataRepository;
    private WeixinSessionAssembler weixinSessionAssembler;

    public WeixinSessionServiceImpl(WeixinService weixinService, WeixinSessionDataRepository weixinSessionDataRepository, WeixinSessionAssembler weixinSessionAssembler) {
        this.weixinService = weixinService;
        this.weixinSessionDataRepository = weixinSessionDataRepository;
        this.weixinSessionAssembler = weixinSessionAssembler;
    }

    @Override
    public WeixinSessionDTO getWeixinSessionByWeixinToken(String weixinToken) {
        WeixinSessionDO weixinSessionDO = weixinSessionDataRepository.findByWeixinToken(weixinToken);
        return weixinSessionAssembler.toDTO(weixinSessionDO);
    }

    @Override
    public WeixinSessionDTO getWeixinSessionById(Long id) {
        WeixinSessionDO weixinSessionDO = weixinSessionDataRepository.findById(id);
        return weixinSessionAssembler.toDTO(weixinSessionDO);
    }

    @Override
    public Long createWeixinSession(CreateWeixinSessionCommand command) {
        WeixinAppUserQuery weixinAppUserQuery = new WeixinAppUserQuery();
        weixinAppUserQuery.setSystemId(command.getSystemId());
        weixinAppUserQuery.setAppId(command.getAppId());
        weixinAppUserQuery.setCode(command.getCode());
        WxAuthUser wxOAuth2UserInfo = weixinService.getWxUserInfo(weixinAppUserQuery);
        if (wxOAuth2UserInfo == null) {
            throw new ValidationException("未获取到微信授权的用户信息");
        }

        WeixinSessionDO weixinSessionDO = new WeixinSessionDO();
        weixinSessionDO.setNickname(wxOAuth2UserInfo.getNickname());
        weixinSessionDO.setOpenId(wxOAuth2UserInfo.getOpenId());
        weixinSessionDO.setUnionId(wxOAuth2UserInfo.getUnionId());
        weixinSessionDO.setAvatar(wxOAuth2UserInfo.getHeadImgUrl());
        weixinSessionDO.setGender(wxOAuth2UserInfo.getSex().byteValue());
        weixinSessionDO.setCountry(wxOAuth2UserInfo.getCountry());
        weixinSessionDO.setProvince(wxOAuth2UserInfo.getProvince());
        weixinSessionDO.setCity(wxOAuth2UserInfo.getCity());
        weixinSessionDO.setSessionKey(wxOAuth2UserInfo.getSessionKey());
        weixinSessionDO.setWeixinToken(UUID.randomUUID().toString());
        weixinSessionDO.setExpireTime(LocalDateTime.now().plusSeconds(sessionExpireTime));
        return weixinSessionDataRepository.saveWeixinSession(weixinSessionDO);
    }

    @Override
    public WeixinSessionDTO createWeixinSession(String appId, WxAuthUser wxOAuth2UserInfo) {
        WeixinSessionDO weixinSessionDO = new WeixinSessionDO();
        weixinSessionDO.setAppId(appId);
        weixinSessionDO.setNickname(wxOAuth2UserInfo.getNickname());
        weixinSessionDO.setOpenId(wxOAuth2UserInfo.getOpenId());
        weixinSessionDO.setUnionId(wxOAuth2UserInfo.getUnionId());
        weixinSessionDO.setAvatar(wxOAuth2UserInfo.getHeadImgUrl());
        weixinSessionDO.setGender(wxOAuth2UserInfo.getSex() != null ? wxOAuth2UserInfo.getSex().byteValue() : 0);
        weixinSessionDO.setCountry(wxOAuth2UserInfo.getCountry());
        weixinSessionDO.setProvince(wxOAuth2UserInfo.getProvince());
        weixinSessionDO.setCity(wxOAuth2UserInfo.getCity());
        weixinSessionDO.setSessionKey(wxOAuth2UserInfo.getSessionKey());
        weixinSessionDO.setWeixinToken(UUID.randomUUID().toString());
        weixinSessionDO.setExpireTime(LocalDateTime.now().plusSeconds(sessionExpireTime));
        Long id = weixinSessionDataRepository.saveWeixinSession(weixinSessionDO);
        return getWeixinSessionById(id);
    }
}
