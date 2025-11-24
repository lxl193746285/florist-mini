package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 员工分类
 *
 * @author legendjw
 */
@Value
public class EmployeeCategory implements ValueObject {
    private Integer id;
    private String name;
}