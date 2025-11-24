package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 组织规模
 *
 * @author legendjw
 */
@Value
public class OrganizationScale implements ValueObject {
    private Integer id;
    private String name;
}
