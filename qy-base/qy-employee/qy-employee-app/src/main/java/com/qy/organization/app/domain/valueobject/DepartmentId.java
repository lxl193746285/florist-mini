package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 部门ID
 *
 * @author legendjw
 */
@Value
public class DepartmentId implements ValueObject {
    private Long id;
}
