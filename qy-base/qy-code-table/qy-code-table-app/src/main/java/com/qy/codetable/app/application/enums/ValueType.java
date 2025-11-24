package com.qy.codetable.app.application.enums;

/**
 * 值类型
 *
 * @author legendjw
 */
public enum ValueType {
    NUMBER("数字"),
    STRING("字符串");

    private String name;

    ValueType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}