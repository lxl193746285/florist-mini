package com.qy.member.app.domain.event;

import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author wwd
 * @date 2023-05-06 10:43
 */
@Getter
public class MemberLogoutEvent extends RemoteApplicationEvent {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 客户端
     */
    private String client;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * ip地址
     */
    private String ip;

    public MemberLogoutEvent() {
    }

    public MemberLogoutEvent(Object source, String originService, Destination destination, Long userId, String client, String userAgent, String ip) {
        super(source, originService, destination);
        this.userId = userId;
        this.client = client;
        this.userAgent = userAgent;
        this.ip = ip;
    }
}