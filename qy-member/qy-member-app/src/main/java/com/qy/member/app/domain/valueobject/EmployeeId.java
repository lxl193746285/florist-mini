package com.qy.member.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 员工id
 *
 * @author legendjw
 */
@Value
public class EmployeeId implements ValueObject {
    private Long id;
}