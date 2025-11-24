package com.qy.identity.app.domain.event;

import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 用户登录事件
 *
 * @author legendjw
 */
@Getter
public class UserLoginEvent extends RemoteApplicationEvent {
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

    public UserLoginEvent() {
    }

    public UserLoginEvent(Object source, String originService, Destination destination, Long userId, String client, String userAgent, String ip) {
        super(source, originService, destination);
        this.userId = userId;
        this.client = client;
        this.userAgent = userAgent;
        this.ip = ip;
    }
}