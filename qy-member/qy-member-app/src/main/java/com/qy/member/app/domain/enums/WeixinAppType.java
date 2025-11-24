package com.qy.member.app.domain.enums;

import java.util.Arrays;

/**
 * 微信应用类型
 *
 * @author legendjw
 */
public enum WeixinAppType {
    MP(1, "公众号"),
    OPEN(2, "开放平台"),
    MINI(3, "小程序");

    private int id;
    private String name;

    public static WeixinAppType getById(int id) {
        return Arrays.stream(WeixinAppType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    WeixinAppType(int id, String name) {
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
