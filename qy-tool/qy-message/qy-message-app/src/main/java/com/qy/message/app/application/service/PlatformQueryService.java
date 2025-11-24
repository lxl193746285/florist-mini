package com.qy.message.app.application.service;

import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.query.PlatformQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

/**
 * 消息平台查询服务
 *
 * @author legendjw
 */
public interface PlatformQueryService {
    /**
     * 查询消息平台
     *
     * @param query
     * @param identity
     * @return
     */
    Page<PlatformDTO> getPlatforms(PlatformQuery query, Identity identity);

    /**
     * 根据ID查询消息平台
     *
     * @param id
     * @return
     */
    PlatformDTO getPlatformById(Long id, Identity identity);

    /**
     * 根据ID查询消息平台
     *
     * @param id
     * @return
     */
    PlatformDTO getPlatformById(Long id);

    /**
     * 根据微信应用id获取消息平台
     *
     * @param weixinAppId
     * @return
     */
    PlatformDTO getPlatformByWeixinAppId(String weixinAppId);
}