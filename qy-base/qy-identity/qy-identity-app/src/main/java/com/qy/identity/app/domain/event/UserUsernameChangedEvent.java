package com.qy.identity.app.domain.event;

import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 用户用户名已变更事件
 *
 * @author legendjw
 */
@Getter
public class UserUsernameChangedEvent extends RemoteApplicationEvent {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 老的用户名
     */
    private String oldUsername;

    /**
     * 新的用户名
     */
    private String newUsername;

    public UserUsernameChangedEvent() {
    }

    public UserUsernameChangedEvent(Object source, String originService, Destination destination, Long userId, String oldUsername, String newUsername) {
        super(source, originService, destination);
        this.userId = userId;
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
    }
}