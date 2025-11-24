package com.qy.identity.api.enums;

import java.util.Arrays;

/**
 * 用户注册来源
 *
 * @author legendjw
 */
public enum UserSource {
    REGISTER("register", "系统注册"),
    ORGANIZATION("organization", "组织加入"),
    OPEN_ACCOUNT("open_account", "开户");

    private String id;
    private String name;

    public static UserSource getById(String id) {
        return Arrays.stream(UserSource.values()).filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    UserSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}