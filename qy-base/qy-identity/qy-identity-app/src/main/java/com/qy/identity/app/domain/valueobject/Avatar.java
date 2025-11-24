package com.qy.identity.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 头像
 *
 * @author legendjw
 */
@Value
public class Avatar implements ValueObject {
    private String url;
}