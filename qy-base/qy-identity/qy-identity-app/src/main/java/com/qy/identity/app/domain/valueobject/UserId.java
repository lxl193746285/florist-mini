package com.qy.identity.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 用户ID
 *
 * @author legendjw
 */
@Value
public class UserId implements ValueObject {
    private Long id;
}