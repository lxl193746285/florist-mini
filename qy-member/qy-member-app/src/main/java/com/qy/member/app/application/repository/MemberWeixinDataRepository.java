package com.qy.member.app.application.repository;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberWeixinDO;

/**
 * 会员微信绑定数据资源库
 *
 * @author legendjw
 */
public interface MemberWeixinDataRepository {
    /**
     * 根据会员系统以及账号查询微信绑定关系
     *
     * @param systemId
     * @param appType
     * @param accountId
     * @return
     */
    MemberWeixinDO findByMemberSystemAndAppTypeAndAccountId(Long systemId, Integer appType, Long accountId);

    MemberWeixinDO findByMemberSystemAndOpenId(Long systemId, String openid);

    MemberWeixinDO findByMemberSystemAndOpenIdAndOrgId(Long organizationId, Long systemId, String openid);
}