package com.qy.authorization.api.command;

import lombok.Data;

/**
 * @author di
 */
@Data
public class CreateEnableAccountCommand {

    private Long userId = 0L;

    private Long organizationId = 0L;

    private Long accountId = 0L;

    private String systemId = "";
}
