package com.qy.organization.app.application.dto;

import lombok.Data;

@Data
public class MemberDTO {

    private Long id;
    private Long organizationId;
    private Long systemId;
    private Long accountId;
    private Integer statusId;
}
