package com.qy.member.app.application.service;

import com.qy.member.app.application.command.CreateMemberClientCommand;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.security.session.MemberIdentity;

import java.util.List;

/**
 * 会员查询服务
 *
 * @author legendjw
 */
public interface MemberClientService {

    MemberClientDTO getMemberClient(Long memberId, String clientId);

    List<MemberClientDTO> getMemberClients(Long memberId);

    void createBatch(CreateMemberClientCommand command, MemberIdentity user);

    void createDefaultClient(Long memberId, Integer identityType);
}