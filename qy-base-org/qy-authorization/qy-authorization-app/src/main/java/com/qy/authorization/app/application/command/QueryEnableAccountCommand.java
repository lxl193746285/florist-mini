package com.qy.authorization.app.application.command;

import lombok.Data;

/**
 * @author di
 */
@Data
public class QueryEnableAccountCommand {

    private Long userId;

    private Long organizationId;

    private Long accountId;

    private String systemId;
}
