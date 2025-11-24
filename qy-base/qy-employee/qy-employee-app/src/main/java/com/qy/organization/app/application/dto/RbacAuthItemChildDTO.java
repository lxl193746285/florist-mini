package com.qy.organization.app.application.dto;

import lombok.Data;

@Data
public class RbacAuthItemChildDTO {

    private String parent;

    private String child;

    private String rule;
}
