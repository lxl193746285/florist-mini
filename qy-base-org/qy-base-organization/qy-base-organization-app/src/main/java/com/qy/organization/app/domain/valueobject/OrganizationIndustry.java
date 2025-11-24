package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 组织行业
 *
 * @author legendjw
 */
@Value
public class OrganizationIndustry implements ValueObject {
    private Integer id;
    private String name;
}
