package com.qy.organization.app.application.enums;

/**
 * 选中类型
 *
 * @author legendjw
 */
public enum CheckedType {
    NONE(0, "不勾"),
    FULL(1, "全勾"),
    HALF(2, "半勾");

    private int id;
    private String name;

    CheckedType(int id, String name) {
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
