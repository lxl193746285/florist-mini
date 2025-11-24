package com.qy.rbac.app.domain.auth;

import java.util.Arrays;

/**
 * 授权项类型
 *
 * @author legendjw
 */
public enum AuthItemType {
    ROLE(1, "角色"),
    PERMISSION(2, "权限节点");

    private int id;
    private String name;

    public static AuthItemType getById(int id) {
        return Arrays.stream(AuthItemType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    AuthItemType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
