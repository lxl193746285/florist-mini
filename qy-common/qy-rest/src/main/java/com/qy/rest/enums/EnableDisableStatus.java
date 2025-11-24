package com.qy.rest.enums;

import java.util.Arrays;

/**
 * 启用禁用状态
 *
 * @author legendjw
 */
public enum EnableDisableStatus {
    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private int id;
    private String name;

    public static EnableDisableStatus getById(int id) {
        return Arrays.stream(EnableDisableStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    public static String getNameById(int id) {
        EnableDisableStatus status = getById(id);
        return status == null ? "" : status.getName();
    }

    EnableDisableStatus(int id, String name) {
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
