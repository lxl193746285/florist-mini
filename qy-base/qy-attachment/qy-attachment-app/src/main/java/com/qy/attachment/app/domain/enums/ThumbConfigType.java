package com.qy.attachment.app.domain.enums;

import java.util.Arrays;

/**
 * 图片缩略配置类型
 *
 * @author legendjw
 */
public enum ThumbConfigType {
    SYSTEM(1, "系统设置"),
    MODULE(2, "模块设置");

    private int id;
    private String name;

    public static ThumbConfigType getById(int id) {
        return Arrays.stream(ThumbConfigType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    ThumbConfigType(int id, String name) {
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
