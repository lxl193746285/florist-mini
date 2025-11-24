package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 组织注册来源数据
 *
 * @author legendjw
 */
@Value
public class OrganizationSourceData implements ValueObject {
    private String source;
    private String sourceName;
    private Long dataId;
    private String dataName;
}
