package com.qy.rbac.app.domain.menu;

import lombok.Builder;
import lombok.Getter;

/**
 * 菜单规则
 *
 * @author legendjw
 */
@Getter
@Builder
public class MenuRule {
    /**
     * id
     */
    private MenuRuleId id;

    /**
     * 菜单id
     */
    private MenuId menuId;

    /**
     * 名称
     */
    private String name;

    /**
     * 范围id
     */
    private String scopeId;

    /**
     * 排序
     */
    private Integer sort;
}
