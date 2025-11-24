package com.qy.member.app.application.service;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.member.app.application.dto.MemberSystemWeixinAppDTO;
import com.qy.member.app.application.query.MemberSystemWeixinAppQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 会员系统微信应用查询服务
 *
 * @author legendjw
 */
public interface MemberSystemWeixinAppQueryService {
    /**
     * 查询会员系统微信应用
     *
     * @param query
     * @param identity
     * @return
     */
    Page<MemberSystemWeixinAppDTO> getMemberSystemWeixinApps(MemberSystemWeixinAppQuery query, Identity identity);

    /**
     * 根据系统id获取会员系统微信应用
     *
     * @param systemId
     * @return
     */
    List<MemberSystemWeixinAppDTO> getMemberSystemWeixinAppBySystemId(Long systemId);

    /**
     * 根据ID查询会员系统微信应用
     *
     * @param id
     * @return
     */
    MemberSystemWeixinAppDTO getMemberSystemWeixinAppById(Long id, Identity identity);

    /**
     * 根据ID查询会员系统微信应用
     *
     * @param id
     * @return
     */
    MemberSystemWeixinAppDTO getMemberSystemWeixinAppById(Long id);

    /**
     * 根据系统id以及应用id获取会员系统微信应用二维码图片
     *
     * @param systemId
     * @param appId
     * @return
     */
    AttachmentBasicDTO getMemberSystemWeixinAppQrCode(Long systemId, String appId);
}