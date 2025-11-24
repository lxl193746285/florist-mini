package com.qy.member.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 开通会员信息
 *
 * @author legendjw
 */
@Data
public class OpenMemberInfoDTO {
    /**
     * 开户会员
     */
    private MemberBasicDTO openMember;

    /**
     * 开户授权的权限组
     */
    private List<RoleBasicDTO> roles = new ArrayList<>();
}
