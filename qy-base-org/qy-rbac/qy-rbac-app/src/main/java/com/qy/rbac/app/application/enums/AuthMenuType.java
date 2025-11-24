package com.qy.rbac.app.application.enums;

import java.util.Arrays;

/**
 * 通用权限操作
 *
 * @author legendjw
 */
public enum AuthMenuType {
    DATA_AUTH(1, "数据权限"),
    PAGE_AUTH(2, "页面权限"),
    ;

    private int id;
    private String name;

    public static AuthMenuType getById(int id) {
        return Arrays.stream(AuthMenuType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    AuthMenuType(int id, String name) {
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
