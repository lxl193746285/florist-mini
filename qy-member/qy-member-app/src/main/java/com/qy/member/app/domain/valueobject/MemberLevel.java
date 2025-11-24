package com.qy.member.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 会员等级
 *
 * @author legendjw
 */
@Value
public class MemberLevel implements ValueObject {
    private Long id;
    private String name;
}
