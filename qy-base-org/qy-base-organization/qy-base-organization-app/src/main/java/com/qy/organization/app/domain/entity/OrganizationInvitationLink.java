package com.qy.organization.app.domain.entity;

import com.qy.ddd.interfaces.Entity;
import com.qy.organization.app.domain.valueobject.InvitationCode;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.organization.app.domain.valueobject.User;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 加入组织邀请链接
 *
 * @author legendjw
 */
@Getter
public class OrganizationInvitationLink implements Entity {
    /**
     * 有效期时长（小时）
     */
    public static int periodOfValidity = 48;

    /**
     * 组织id
     */
    private OrganizationId organizationId;

    /**
     * 邀请码
     */
    private InvitationCode code;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 失效时间
     */
    private LocalDateTime failureTime;

    /**
     * 创建人
     */
    private User creator;

    public OrganizationInvitationLink(OrganizationId organizationId, InvitationCode code, User creator) {
        this.organizationId = organizationId;
        this.code = code;
        this.creator = creator;
        this.createTime = LocalDateTime.now();
        this.failureTime = this.createTime.plusHours(periodOfValidity);
    }

    public OrganizationInvitationLink(OrganizationId organizationId, InvitationCode code, LocalDateTime createTime, LocalDateTime failureTime, User creator) {
        this.organizationId = organizationId;
        this.code = code;
        this.createTime = createTime;
        this.failureTime = failureTime;
        this.creator = creator;
    }

    /**
     * 获取访问链接
     *
     * @param frontAccessUrl
     * @return
     */
    public String getLink(String frontAccessUrl) {
        return frontAccessUrl.replace("{code}", this.code.getCode());
    }

    /**
     * 链接是否有效
     *
     * @return
     */
    public boolean isValid() {
        return LocalDateTime.now().isBefore(this.failureTime);
    }
}