package com.qy.organization.app.application.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 切换组织命令
 *
 * @author legendjw
 */
@Data
public class SwitchOrganizationCommand implements Serializable {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 客户端id
     */
    private String clientId;

    private Long systemId;

    private Long appId;
}