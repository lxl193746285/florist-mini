package com.qy.organization.app.domain.enums;

import java.util.Arrays;

/**
 * 员工权限类型
 *
 * @author legendjw
 */
public enum EmployeePermissionType {
    PERMISSION_GROUP(1, "权限组"),
    INDEPENDENT(2, "独立权限");

    private int id;
    private String name;

    public static EmployeePermissionType getById(int id) {
        return Arrays.stream(EmployeePermissionType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    EmployeePermissionType(int id, String name) {
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
