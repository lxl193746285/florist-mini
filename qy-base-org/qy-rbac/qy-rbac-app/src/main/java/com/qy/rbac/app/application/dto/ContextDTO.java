package com.qy.rbac.app.application.dto;

import lombok.Value;

/**
 * 上下文DTO
 *
 * @author legendjw
 */
@Value
public class ContextDTO {
    /**
     * 上下文
     */
    private String context;
    /**
     * 上下文id
     */
    private String contextId;
}