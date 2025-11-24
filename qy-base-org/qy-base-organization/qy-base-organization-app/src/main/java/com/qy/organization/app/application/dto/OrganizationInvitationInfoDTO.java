package com.qy.organization.app.application.dto;

import lombok.Data;

/**
 * 组织邀请链接信息
 *
 * @author legendjw
 */
@Data
public class OrganizationInvitationInfoDTO {
    /**
     * 邀请加入的组织
     */
    private OrganizationBasicDTO organization;

    /**
     * 邀请人姓名
     */
    private String inviterName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 失效时间
     */
    private String failureTime;
}