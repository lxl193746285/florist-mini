package com.qy.member.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 会员id
 *
 * @author legendjw
 */
@Value
public class MemberId implements ValueObject {
    private Long id;
}