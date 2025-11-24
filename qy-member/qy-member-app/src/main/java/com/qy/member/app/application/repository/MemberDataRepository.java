package com.qy.member.app.application.repository;

import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.rest.pagination.Page;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 会员数据资源库
 *
 * @author legendjw
 */
public interface MemberDataRepository {
    /**
     * 根据id获取会员
     *
     * @param id
     * @return
     */
    MemberDO findById(Long id);

    /**
     * 批量获取会员
     *
     * @param ids
     * @return
     */
    List<MemberDO> findByIds(List<Long> ids);

    /**
     * 根据查询获取会员
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<MemberDO> findMemberByQuery(MemberQuery query, MultiOrganizationFilterQuery filterQuery);

    MemberDO findByPhoneSystem(String phone, Long systemId, Long orgId);

    MemberDO findByAccountSystem(Long accountId, Long systemId, Long orgId);

    List<MemberBasicDTO> getMembersByAccount(Long accountId);
    List<MemberDO> getMembersByAccountAndSystemId(Long accountId, Long systemId);
}