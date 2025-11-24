package com.qy.attachment.app.domain.enums;

import java.util.Arrays;

/**
 * 文件存储方式
 *
 * @author legendjw
 */
public enum StorageMode {
    LOCAL(1, "本地存储"),
    ALIYUN_OSS(2, "阿里云OSS");

    private int id;
    private String name;

    StorageMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static StorageMode getById(int id) {
        return Arrays.stream(StorageMode.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }
}
