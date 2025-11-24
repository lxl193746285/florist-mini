package com.qy.member.app.domain.event;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 会员状态已更新事件
 *
 * @author legendjw
 */
public class MemberStatusUpdatedEvent extends RemoteApplicationEvent {
    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 状态
     */
    private Integer statusId;

    public MemberStatusUpdatedEvent() {
    }

    public MemberStatusUpdatedEvent(Object source, String originService, Destination destination, Long memberId, Integer statusId) {
        super(source, originService, destination);
        this.memberId = memberId;
        this.statusId = statusId;
    }
}