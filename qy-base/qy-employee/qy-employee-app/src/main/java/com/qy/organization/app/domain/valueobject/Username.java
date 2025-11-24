package com.qy.organization.app.domain.valueobject;

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
}