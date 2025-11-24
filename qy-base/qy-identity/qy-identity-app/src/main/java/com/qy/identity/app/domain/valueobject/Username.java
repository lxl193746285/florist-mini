package com.qy.identity.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 用户名
 *
 * @author legendjw
 */
@Value
public class Username implements ValueObject {
    private String name;

    public Username(String name) {
        this.name = name.trim();
    }
}