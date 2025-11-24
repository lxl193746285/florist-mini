package com.qy.organization.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本权限组信息
 *
 * @author legendjw
 */
@Data
public class RoleBasicDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;


    private String contextId;
}
