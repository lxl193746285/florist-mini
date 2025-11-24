package com.qy.organization.app.domain.enums;

import java.util.Arrays;

/**
 * 组织状态
 *
 * @author legendjw
 */
public enum OrganizationStatus {
    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private int id;
    private String name;

    public static OrganizationStatus getById(int id) {
        return Arrays.stream(OrganizationStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    OrganizationStatus(int id, String name) {
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
