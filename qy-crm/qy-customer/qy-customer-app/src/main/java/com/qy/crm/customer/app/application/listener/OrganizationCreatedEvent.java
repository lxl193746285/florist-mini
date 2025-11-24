package com.qy.crm.customer.app.application.listener;

import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 组织已创建事件
 *
 * @author legendjw
 */
@Getter
public class OrganizationCreatedEvent extends RemoteApplicationEvent {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 创建来源
     */
    private String createSource;

    public OrganizationCreatedEvent() {
    }

    public OrganizationCreatedEvent(Object source, String originService, Destination destination, Long organizationId, String name, String createSource) {
        super(source, originService, destination);
        this.organizationId = organizationId;
        this.name = name;
        this.createSource = createSource;
    }
}
