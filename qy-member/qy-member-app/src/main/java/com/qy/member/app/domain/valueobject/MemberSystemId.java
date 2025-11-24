package com.qy.member.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 系统ID
 *
 * @author legendjw
 */
@Value
public class MemberSystemId implements ValueObject {
    private Long id;
}
