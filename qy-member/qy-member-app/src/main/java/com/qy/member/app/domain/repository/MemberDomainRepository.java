package com.qy.member.app.domain.repository;

import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.valueobject.*;

import java.util.List;

/**
 * 会员领域资源库
 *
 * @author legendjw
 */
public interface MemberDomainRepository {
    /**
     * 根据id获取会员
     *
     * @param memberId
     * @return
     */
    Member findById(MemberId memberId);

    /**
     * 根据手机号获取会员
     *
     * @param memberSystemId
     * @param phone
     * @return
     */
    Member findByMemberSystemAndPhone(MemberSystemId memberSystemId, PhoneNumber phone, Long organization);

    /**
     * 根据会员系统以及账号id查询绑定的会员
     *
     * @param memberSystemId
     * @param accountId
     * @param organizationId
     * @return
     */
    Member findByMemberSystemAndAccountId(MemberSystemId memberSystemId, AccountId accountId, OrganizationId organizationId);

    /**
     * 根据会员系统以及账号id查询绑定的会员，默认取第一条，登录账号使用
     *
     * @param memberSystemId
     * @param accountId
     * @return
     */
    Member findDefaultByMemberSystemAndAccountId(MemberSystemId memberSystemId, AccountId accountId);

    /**
     * 根据账号id查询绑定的所有会员
     *
     * @param accountId
     * @return
     */
    List<Member> findByAccountId(AccountId accountId);

    /**
     * 保存会员
     *
     * @param member
     * @return
     */
    MemberId saveMember(Member member);

    MemberId saveMemberWithoutAccount(Member member);

    /**
     * 指定账号下绑定的会员是否全部删除
     *
     * @param accountId
     * @return
     */
    boolean isAccountAllMemberRemoved(AccountId accountId);

    /**
     * 删除会员
     *
     * @param memberId
     */
    void removeMember(MemberId memberId);

    /**
     * 重置密码
     *
     * @param memberId
     */
    void resetMemberPassword(MemberId memberId);

    /**
     * 解除会员微信绑定关系
     *
     * @param memberId
     */
    void unbindMemberWeixin(MemberId memberId);

}