package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 含有规则的权限节点
 *
 * @author legendjw
 */
@Value
public class PermissionWithRule implements ValueObject {
    /**
     * 权限
     */
    private PermissionId permissionId;
    /**
     * 权限规则
     */
    private Rule rule;
}