package com.qy.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 权限组人员
 *
 * @author legendjw
 */
@Data
@AllArgsConstructor
public class RoleUserDTO {
    private Long id;
    private String name;
}
