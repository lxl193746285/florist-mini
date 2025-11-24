package com.qy.member.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 会员账号id
 *
 * @author legendjw
 */
@Value
public class AccountId implements ValueObject {
    private Long id;
}