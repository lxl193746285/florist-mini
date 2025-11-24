package com.qy.rbac.app.domain.menu;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Value;

/**
 * 菜单id
 *
 * @author legendjw
 */
@Value
public class MenuId implements ValueObject {
    private Long id;
}
