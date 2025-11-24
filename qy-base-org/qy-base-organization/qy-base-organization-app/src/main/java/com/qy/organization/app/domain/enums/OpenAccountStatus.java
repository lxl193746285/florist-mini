package com.qy.organization.app.domain.enums;

import java.util.Arrays;

/**
 * 组织开户状态
 *
 * @author legendjw
 */
public enum OpenAccountStatus {
    NOT_OPENED(0, "未开户"),
    OPENED(1, "已开户");

    private int id;
    private String name;

    public static OpenAccountStatus getById(int id) {
        return Arrays.stream(OpenAccountStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    OpenAccountStatus(int id, String name) {
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
