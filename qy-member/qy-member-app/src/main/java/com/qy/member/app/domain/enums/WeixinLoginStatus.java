package com.qy.member.app.domain.enums;

import java.util.Arrays;

/**
 * 微信登录状态
 *
 * @author legendjw
 */
public enum WeixinLoginStatus {
    SUCCESS(1, "成功登录"),
    NOT_BIND(2, "未绑定账号"),
    NOT_FOLLOW(3, "未关注公众号"),
    FAILED(4, "账号已绑定其他账号"),
    ;


    private int id;
    private String name;

    public static WeixinLoginStatus getById(int id) {
        return Arrays.stream(WeixinLoginStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    WeixinLoginStatus(int id, String name) {
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
