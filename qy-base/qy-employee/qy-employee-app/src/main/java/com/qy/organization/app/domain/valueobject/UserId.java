package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 全局用户id
 *
 * @author legendjw
 */
@Value
public class UserId implements ValueObject {
    private Long id;
}
