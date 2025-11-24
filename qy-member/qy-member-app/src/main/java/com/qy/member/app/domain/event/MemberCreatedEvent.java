package com.qy.member.app.domain.event;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 会员已创建事件
 *
 * @author legendjw
 */
public class MemberCreatedEvent extends RemoteApplicationEvent {
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

    public MemberCreatedEvent() {
    }

    public MemberCreatedEvent(Object source, String originService, Destination destination, Long memberId, Long accountId, String name, String phone, String avatar) {
        super(source, originService, destination);
        this.memberId = memberId;
        this.accountId = accountId;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
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