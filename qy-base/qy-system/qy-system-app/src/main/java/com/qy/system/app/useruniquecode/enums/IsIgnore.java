package com.qy.system.app.useruniquecode.enums;

import java.util.Arrays;

/**
 * 用户状态
 *
 * @author legendjw
 */
public enum IsIgnore {
    ENABLE(1, "忽略");

    private int id;
    private String name;

    public static IsIgnore getById(int id) {
        return Arrays.stream(IsIgnore.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    IsIgnore(int id, String name) {
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
