package com.qy.identity.app.domain.enums;

import java.util.Arrays;

/**
 * 用户状态
 *
 * @author legendjw
 */
public enum UserStatus {
    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private int id;
    private String name;

    public static UserStatus getById(int id) {
        return Arrays.stream(UserStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    UserStatus(int id, String name) {
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
