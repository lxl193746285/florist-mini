package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 角色id
 *
 * @author legendjw
 */
@Value
public class RoleId implements ValueObject {
    private String id;
}
