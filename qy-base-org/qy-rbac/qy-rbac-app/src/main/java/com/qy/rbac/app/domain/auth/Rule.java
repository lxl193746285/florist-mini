package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.Entity;
import lombok.Getter;

/**
 * 权限规则
 *
 * @author legendjw
 */
@Getter
public class Rule implements Entity {
    /**
     * id
     */
    private String id;

    /**
     * 数据
     */
    private Object data;

    public Rule(String id) {
        this.id = id;
    }

    public Rule(String id, Object data) {
        this.id = id;
        this.data = data;
    }
}