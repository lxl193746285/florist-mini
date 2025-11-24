package com.qy.member.app.application.repository;

import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 账号数据资源库
 *
 * @author legendjw
 */
public interface AccountDataRepository {
    /**
     * 根据id获取账号
     *
     * @param id
     * @return
     */
    MemberAccountDO findById(Long id);

    /**
     * 获取指定会员的主账号
     *
     * @param memberId
     * @return
     */
    MemberAccountDO findMemberPrimaryAccount(Long memberId);

    /**
     * 获取指定会员的子账号
     *
     * @param memberId
     * @return
     */
    List<MemberAccountDO> findMemberChildAccounts(Long memberId);

    /**
     * 根据查询获取账号
     *
     * @param query
     * @return
     */
    Page<MemberAccountDO> findByQuery(AccountQuery query);

    /**
     * 根据手机号获取账号
     *
     * @param phone
     * @return
     */
    MemberAccountDO findByPhone(String phone);

}