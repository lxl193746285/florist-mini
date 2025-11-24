package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 员工ID
 *
 * @author legendjw
 */
@Value
public class EmployeeId implements ValueObject {
    private Long id;
}
