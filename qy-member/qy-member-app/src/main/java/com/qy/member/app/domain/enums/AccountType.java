package com.qy.member.app.domain.enums;

import java.util.Arrays;

/**
 * 账号类型
 *
 * @author legendjw
 */
public enum AccountType {
    PRIMARY(1, "主账号"),
    CHILD(2, "子账号");

    private int id;
    private String name;

    public static AccountType getById(int id) {
        return Arrays.stream(AccountType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    AccountType(int id, String name) {
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
