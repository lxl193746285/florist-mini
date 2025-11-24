package com.qy.organization.app.application.dto;

import lombok.Data;

/**
 * 组织邀请链接
 *
 * @author legendjw
 */
@Data
public class OrganizationInvitationLinkDTO {
    /**
     * 链接地址
     */
    private String link;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 失效时间
     */
    private String failureTime;
}