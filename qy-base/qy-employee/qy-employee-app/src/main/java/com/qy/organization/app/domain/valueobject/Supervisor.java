package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 上级领导
 *
 * @author legendjw
 */
@Value
public class Supervisor implements ValueObject {
    private Long id;
    private String name;

    public Supervisor(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
