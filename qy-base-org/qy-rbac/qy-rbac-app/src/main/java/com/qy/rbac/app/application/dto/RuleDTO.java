package com.qy.rbac.app.application.dto;

import lombok.Data;

/**
 * 规则DTO
 *
 * @author legendjw
 */
@Data
public class RuleDTO {
    /**
     * id
     */
    private String id;

    /**
     * 数据
     */
    private Object data;
}
