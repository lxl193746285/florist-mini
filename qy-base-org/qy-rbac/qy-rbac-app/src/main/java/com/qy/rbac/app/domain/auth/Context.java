package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 上下文
 *
 * @author legendjw
 */
@Value
public class Context implements ValueObject {
    private String context;
    private String contextId;
}
