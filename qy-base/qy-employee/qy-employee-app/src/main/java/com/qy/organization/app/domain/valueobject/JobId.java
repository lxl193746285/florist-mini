package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 岗位ID
 *
 * @author legendjw
 */
@Value
public class JobId implements ValueObject {
    private Long id;
}
