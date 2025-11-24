package com.qy.member.app.application.repository;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.WeixinSessionDO;

/**
 * 微信会话数据资源库
 *
 * @author legendjw
 */
public interface WeixinSessionDataRepository {
    /**
     * 根据微信token获取微信会话
     *
     * @param weixinToken
     * @return
     */
    WeixinSessionDO findByWeixinToken(String weixinToken);

    /**
     * 根据id获取微信会话
     *
     * @param id
     * @return
     */
    WeixinSessionDO findById(Long id);

    /**
     * 保存微信会话
     *
     * @param weixinSessionDO
     * @return
     */
    Long saveWeixinSession(WeixinSessionDO weixinSessionDO);

}