package com.qy.rbac.app.domain.share;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 人员
 *
 * @author legendjw
 */
@Value
public class User implements ValueObject {
    private Long id;
    private String name;
}