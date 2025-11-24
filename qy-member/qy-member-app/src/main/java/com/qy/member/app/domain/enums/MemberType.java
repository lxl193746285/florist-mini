package com.qy.member.app.domain.enums;

import java.util.Arrays;

/**
 * 账号类型
 *
 * @author lxl
 */
public enum MemberType {
    EMPLOYEE(1, "员工"),
    CUSTOMER(2, "经销商"),
    CUSTOMER_EMPLOYEE(3, "经销商员工"),

    MARKET(4, "全民营销"),
    ;

    private int id;
    private String name;

    public static MemberType getById(int id) {
        return Arrays.stream(MemberType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    MemberType(int id, String name) {
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
