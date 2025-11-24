package com.qy.authorization.app.application.command;

import lombok.Data;

@Data
public class DeleteEnableAccountCommand {

    private Long userId = 0L;

    private Long organizationId = 0L;

    private Long accountId = 0L;

    private String systemId = "";
}
