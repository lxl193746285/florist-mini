package com.qy.message.app.application.enums;

public enum MessageSmsScene {
    SCREEN_LOTTERY_WIN("SCREEN_LOTTERY_WIN", "会议大屏中奖短信", "271554719"),
    ;

    private String scene;

    private String description;

    private String code;

    MessageSmsScene(String scene, String description, String code) {
        this.scene = scene;
        this.description = description;
        this.code = code;
    }

    public String getScene() {
        return scene;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static MessageSmsScene getByScene(String scene) {
        for (MessageSmsScene smsScene : MessageSmsScene.values()) {
            if (smsScene.getScene().equals(scene)) {
                return smsScene;
            }
        }
        return null;
    }
}
