package com.qy.message.app.application.enums;

public enum MessageType {

    SYSTEM(1,"系统消息"),
    USER(2,"用户消息"),
    ;

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MessageType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 根据值获取名称
     * @param id
     * @return
     */
    public static String getName(int id) {
        for (MessageType m : MessageType.values()) {
            if (m.getId() == id) {
                return m.name;
            }
        }
        return null;
    }
}
