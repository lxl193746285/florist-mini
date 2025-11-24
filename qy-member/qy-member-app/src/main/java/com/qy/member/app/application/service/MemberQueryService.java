package com.qy.member.app.application.service;

import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 会员查询服务
 *
 * @author legendjw
 */
public interface MemberQueryService {
    /**
     * 获取会员列表
     *
     * @return
     */
    Page<MemberDTO> getMembers(MemberQuery query, Identity identity);

    /**
     * 获取会员的基本信息
     *
     * @param id
     * @return
     */
    MemberBasicDTO getBasicMember(Long id);

    /**
     * 获取会员的信息
     *
     * @param id
     * @return
     */
    MemberDTO getMember(Long id);

    /**
     * 获取会员的信息
     *
     * @param id
     * @param identity
     * @return
     */
    MemberDetailDTO getDetailMember(Long id, Identity identity);

    /**
     * 批量获取会员的基本信息
     *
     * @param ids
     * @return
     */
    List<MemberBasicDTO> getBasicMembers(List<Long> ids);

    /**
     * 获取开通会员信息
     *
     * @param memberId
     * @return
     */
    OpenMemberInfoDTO getOpenMemberInfo(Long memberId);

    /**
     * 获取会员审核信息
     *
     * @param memberId
     * @return
     */
    MemberAuditInfoDTO getMemberAuditInfo(Long memberId);

    /**
     * 根据组织手机号会员系统获取会员信息
     *
     * @param phone
     * @param systemId
     * @param orgId
     * @return
     */
    MemberBasicDTO getMemberByPhoneSystemId(String phone, Long systemId, Long orgId);

    MemberBasicDTO getMemberByAccountSystemId(Long accountId, Long systemId, Long orgId);

    List<MemberBasicDTO> getMembersByAccount(Long accountId);

    List<MemberBasicDTO> getMembersByAccountAndSystemId(Long accountId, Long systemId);
}