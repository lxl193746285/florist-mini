package com.qy.rbac.app.domain.menu;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 模块id
 *
 * @author legendjw
 */
@Value
public class ModuleId implements ValueObject {
    private Long id;
}
