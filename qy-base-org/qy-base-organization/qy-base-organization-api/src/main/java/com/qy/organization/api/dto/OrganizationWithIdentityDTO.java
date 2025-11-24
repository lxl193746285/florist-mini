package com.qy.organization.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织员工身份DTO
 *
 * @author legendjw
 */
@Data
public class OrganizationWithIdentityDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 组织logo
     */
    private String logo;

    /**
     * 员工身份
     */
    private EmployeeBasicDTO employee;
}