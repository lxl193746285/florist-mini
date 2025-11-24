package com.qy.member.app.application.repository;

import com.qy.member.app.application.dto.MemberClientDTO;
import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberClientDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 账号数据资源库
 *
 * @author legendjw
 */
public interface MemberClientRepository {

    List<MemberClientDTO> findMemberClientList(Long memberId);

    MemberClientDTO findMemberClientList(Long memberId, String clientId);

    void saveBatch(List<MemberClientDO> memberClientDOS);

    void removeMemberClient(Long memberId);
}