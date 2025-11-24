package com.qy.organization.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本岗位信息
 *
 * @author legendjw
 */
@Data
public class JobBasicDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 名称
     */
    private String name;
}
