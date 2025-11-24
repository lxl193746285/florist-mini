package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * LOGO
 *
 * @author legendjw
 */
@Value
public class Logo implements ValueObject {
    private String url;
}
