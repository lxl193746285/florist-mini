package com.qy.attachment.app.domain.enums;

import java.util.Arrays;

public enum ImageHandle {

    WEB(1, "前端处理"),
    SERVER(2, "服务器处理");

    private int id;
    private String name;

    ImageHandle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static ImageHandle getById(int id) {
        return Arrays.stream(ImageHandle.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }
}
