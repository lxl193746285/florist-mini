package com.qy.member.app.domain.event;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 会员账号已更新事件
 *
 * @author legendjw
 */
public class MemberAccountUpdatedEvent extends RemoteApplicationEvent {
    /**
     * 账号类型 1：主账号 2：子账号
     */
    private Integer accountType;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    public MemberAccountUpdatedEvent() {
    }

    public MemberAccountUpdatedEvent(Object source, String originService, Destination destination, Integer accountType, Long memberId, Long accountId, String name, String phone, String avatar) {
        super(source, originService, destination);
        this.accountType = accountType;
        this.memberId = memberId;
        this.accountId = accountId;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }
}