package com.qy.authorization.app.application.dto;

import lombok.Data;

/**
 * @author di
 */
@Data
public class EnableAccountDTO {

    private Long userId;

    private Long organizationId;

    private Long accountId;

    private String systemId;

}
