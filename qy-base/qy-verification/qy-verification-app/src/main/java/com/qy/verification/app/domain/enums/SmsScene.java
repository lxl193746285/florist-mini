package com.qy.verification.app.domain.enums;

public enum SmsScene {
    REGISTER_MEMBER("REGISTER_MEMBER", "注册会员", "207875098"),
    MEMBER_LOGIN("MEMBER_LOGIN", "会员登录", "207875100"),
    RETRIEVE_PASSWORD("RETRIEVE_PASSWORD", "会员忘记密码找回密码", "207875097"),
    MEMBER_CHANGE_PHONE("MEMBER_CHANGE_PHONE", "会员更换手机号", "207875096"),
    MODIFY_MEMBER_PASSWORD("MODIFY_MEMBER_PASSWORD", "修改会员密码", "207875097"),;

    private String scene;

    private String description;

    private String code;

    SmsScene(String scene, String description, String code) {
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

    public static SmsScene getByScene(String scene) {
        for (SmsScene smsScene : SmsScene.values()) {
            if (smsScene.getScene().equals(scene)) {
                return smsScene;
            }
        }
        return null;
    }
}
