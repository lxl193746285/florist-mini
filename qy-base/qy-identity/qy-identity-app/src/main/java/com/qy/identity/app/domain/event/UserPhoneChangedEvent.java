package com.qy.identity.app.domain.event;

import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 用户手机号已变更事件
 *
 * @author legendjw
 */
@Getter
public class UserPhoneChangedEvent extends RemoteApplicationEvent {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 老的手机号
     */
    private String oldPhone;

    /**
     * 新的手机号
     */
    private String newPhone;

    public UserPhoneChangedEvent() {
    }

    public UserPhoneChangedEvent(Object source, String originService, Destination destination, Long userId, String oldPhone, String newPhone) {
        super(source, originService, destination);
        this.userId = userId;
        this.oldPhone = oldPhone;
        this.newPhone = newPhone;
    }
}