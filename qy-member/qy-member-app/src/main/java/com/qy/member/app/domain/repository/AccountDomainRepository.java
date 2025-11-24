package com.qy.member.app.domain.repository;

import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.valueobject.*;

/**
 * 会员账号领域资源库
 *
 * @author legendjw
 */
public interface AccountDomainRepository {
    /**
     * 根据id获取账号
     *
     * @param accountId
     * @return
     */
    MemberAccount findById(AccountId accountId);

    /**
     * 查找登录账号
     *
     * @param memberSystemId
     * @param phone
     * organizationId
     * @return
     */
    MemberAccount findLoginAccount(MemberSystemId memberSystemId, PhoneNumber phone, OrganizationId organizationId);

    /**
     * 查找登录账号
     *
     * @param phone
     * @return
     */
    MemberAccount findLoginAccount(PhoneNumber phone);

    /**
     * 查找登录账号
     *
     * @param memberSystemId
     * @param accountId
     * @return
     */
    MemberAccount findLoginAccount(MemberSystemId memberSystemId, AccountId accountId, OrganizationId organizationId);

    /**
     * 根据手机号获取账号
     *
     * @param phone
     * @return
     */
    MemberAccount findByPhone(PhoneNumber phone);

    /**
     * 保存账号
     *
     * @param memberAccount
     * @return
     */
    AccountId saveAccount(MemberAccount memberAccount);

    /**
     * 删除指定账号id对应的账号
     *
     * @param accountId
     */
    void removeAccountByAccountId(AccountId accountId);

}