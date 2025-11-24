package com.qy.message.app.application.enums;

public enum MessageLogType {

    STATION_LETTER(1,"站内信"),
    WECHAT(2,"微信"),
    SMS(3,"短信"),
    EMAIL(4,"邮件"),
    APP(5,"APP推送"),
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

    public byte getByte() {
        return (byte)id;
    }

    MessageLogType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String idToName(Byte id) {
        if (id == null) {
            return null;
        }
        for (MessageLogType m : MessageLogType.values()) {
            if (id.intValue() == m.getId()) {
                return m.getName();
            }
        }
        return null;
    }
}
