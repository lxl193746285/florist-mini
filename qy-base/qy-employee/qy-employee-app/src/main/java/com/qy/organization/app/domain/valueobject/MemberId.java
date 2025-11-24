package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 全局会员id
 *
 * @author lxl
 */
@Value
public class MemberId implements ValueObject {
    private Long id;
}
