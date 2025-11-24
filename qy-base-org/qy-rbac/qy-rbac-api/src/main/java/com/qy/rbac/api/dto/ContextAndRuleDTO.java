package com.qy.rbac.api.dto;

import lombok.Value;

/**
 * 上下文DTO
 *
 * @author legendjw
 */
@Value
public class ContextAndRuleDTO {
    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 规则范围id
     */
    private String ruleScopeId;

    /**
     * 规则范围数据
     */
    private Object ruleScopeData;
}
