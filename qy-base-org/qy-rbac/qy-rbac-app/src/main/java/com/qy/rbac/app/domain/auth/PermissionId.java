package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 权限id
 *
 * @author legendjw
 */
@Value
public class PermissionId implements ValueObject {
    private String id;
}
