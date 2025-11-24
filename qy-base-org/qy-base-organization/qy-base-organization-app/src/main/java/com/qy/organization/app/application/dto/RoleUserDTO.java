package com.qy.organization.app.application.dto;

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
    /**
     * 人员id
     */
    private Long id;
    /**
     * 人员名称
     */
    private String name;
}
