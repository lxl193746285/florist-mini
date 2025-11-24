package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 组织ID
 *
 * @author legendjw
 */
@Value
public class OrganizationId implements ValueObject {
    private Long id;
}
