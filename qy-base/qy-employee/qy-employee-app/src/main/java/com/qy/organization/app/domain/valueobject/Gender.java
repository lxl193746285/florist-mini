package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 性别
 *
 * @author legendjw
 */
@Value
public class Gender implements ValueObject {
    private Integer id;
    private String name;
}
