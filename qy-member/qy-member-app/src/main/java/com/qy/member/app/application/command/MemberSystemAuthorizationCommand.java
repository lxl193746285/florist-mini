package com.qy.member.app.application.command;

import lombok.Data;

import java.util.List;

/**
 * @author wwd
 * @since 2023-10-24 15:36
 */
@Data
public class MemberSystemAuthorizationCommand {

    /**
     * 授予的会员系统
     */
    private List<Long> systemIds;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 来源（1创建2授权）
     */
    private Integer source;
}
