package com.qy.rbac.app.domain.menu;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 菜单规则id
 *
 * @author legendjw
 */
@Value
public class MenuRuleId implements ValueObject {
    private Long id;
}
