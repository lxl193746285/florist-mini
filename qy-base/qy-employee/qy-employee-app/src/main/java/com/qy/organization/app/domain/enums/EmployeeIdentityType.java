package com.qy.organization.app.domain.enums;

import java.util.Arrays;

/**
 * 员工身份类型
 *
 * @author legendjw
 */
public enum EmployeeIdentityType {
    EMPLOYEE(0, "无权限"),
    CREATOR(1, "超管"),
    ADMIN(2, "管理员"),
    OPERATOR(3, "操作员");

    private int id;
    private String name;

    public static EmployeeIdentityType getById(int id) {
        return Arrays.stream(EmployeeIdentityType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    EmployeeIdentityType(int id, String name) {
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
