package com.qy.member.app.application.service;

import com.qy.member.app.application.dto.SystemPassportQrcodeDTO;
import com.qy.security.session.MemberIdentity;

import javax.servlet.http.HttpServletRequest;

/**
 * 账号查询服务
 *
 * @author wwd
 */
public interface SystemPassportQrcodeService {

    /**
     * 获取轮训二维码
     * @param uuid
     * @param linkType
     * @param clientId
     * @return
     */
    String getLoginPassport(String uuid, String linkType, String clientId);

    /**
     * 获取轮训二维码（微信）
     * @param uuid
     * @param linkType
     * @param clientId
     * @return
     */
    String getWeixinPassport(String uuid, String linkType, String clientId, String path);

    void action(String uuid, Integer status, HttpServletRequest request, MemberIdentity user);

    SystemPassportQrcodeDTO check(String uuid, String type);

}