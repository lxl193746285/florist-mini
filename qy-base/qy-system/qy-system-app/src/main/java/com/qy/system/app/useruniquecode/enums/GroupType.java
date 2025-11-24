package com.qy.system.app.useruniquecode.enums;

import java.util.Arrays;

/**
 * 用户状态
 *
 * @author legendjw
 */
public enum GroupType {
    CUSTOMER(1, "经销商"),
    EMPLOYEE(1, "公司员工"),
    ;

    private int id;
    private String name;

    public static GroupType getById(int id) {
        return Arrays.stream(GroupType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    GroupType(int id, String name) {
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
