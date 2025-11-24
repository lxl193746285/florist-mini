package com.qy.member.app.domain.repository;

import com.qy.member.app.domain.entity.AccountWeixin;
import com.qy.member.app.domain.valueobject.AccountId;
import com.qy.member.app.domain.valueobject.MemberId;
import com.qy.member.app.domain.valueobject.MemberSystemId;

/**
 * 微信账号微信领域资源库
 *
 * @author legendjw
 */
public interface AccountWexinDomainRepository {
    /**
     * 查找账号微信绑定
     *
     * @param accountId
     * @param organizationId
     * @return
     */
    AccountWeixin findAccountWeixinByAccountId(String appId, AccountId accountId, Long organizationId);
    /**
     * 查找账号微信绑定
     *
     * @param accountId
     * @return
     */
    AccountWeixin findAccountWeixinByMemberId(String appId, MemberId memberId, AccountId accountId);

    /**
     * 查找账号微信绑定
     *
     * @param openId
     * @param unionId
     * @return
     */
    AccountWeixin findAccountWeixinByOpenIdAndUnionId(String appId, String openId, String unionId);

    /**
     * 查找账号微信绑定
     *
     * @param openId
     * @param unionId
     * @return
     */
    AccountWeixin findAccountWeixinByOrganization(String appId, String openId, String unionId, Long organizationId);

    /**
     * 查询指定openid以及unionid绑定的账号数量
     *
     * @param appId
     * @param openId
     * @param unionId
     * @return
     */
    int countAccountWeixinByOpenIdAndUnionId(String appId, String openId, String unionId);

    /**
     * 查询指定openid以及unionid绑定的账号数量
     *
     * @param appId
     * @param openId
     * @param unionId
     * @return
     */
    int countAccountWeixinByOrganization(String appId, String openId, String unionId, Long organizationId);

    /**
     * 保存账号微信关系
     *
     * @param accountWeixin
     */
    void save(AccountWeixin accountWeixin);

    /**
     * 删除会员绑定微信关系
     *
     * @param accountId
     */
    void removeMemberAccountWeixinByAccountId(AccountId accountId);

    /**
     * 删除会员系统下会员绑定微信关系
     *
     * @param accountId
     */
    void removeMemberAccountWeixinByAccountIdAndSystemId(AccountId accountId, MemberSystemId memberSystemId);
}