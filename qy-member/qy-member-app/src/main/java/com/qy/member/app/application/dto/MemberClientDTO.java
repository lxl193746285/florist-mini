package com.qy.member.app.application.dto;

import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员客户端详情
 *
 * @author legendjw
 */
@Data
public class MemberClientDTO {

    private Long memberId;

    private String clientId;

    private String clientName;
}
