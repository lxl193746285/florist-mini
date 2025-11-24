package com.qy.identity.app.domain.event;

import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 用户已创建事件
 *
 * @author legendjw
 */
@Getter
public class UserCreatedEvent extends RemoteApplicationEvent {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 创建来源
     */
    private String createSource;

    public UserCreatedEvent() {
    }

    public UserCreatedEvent(Object source, String originService, Destination destination, Long userId, String name, String username, String phone, String email, String avatar, String createSource) {
        super(source, originService, destination);
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.createSource = createSource;
    }
}