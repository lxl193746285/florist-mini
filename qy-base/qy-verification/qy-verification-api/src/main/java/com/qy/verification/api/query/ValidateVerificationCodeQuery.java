package com.qy.verification.api.query;

import javax.validation.constraints.NotBlank;

/**
 * 验证验证码查询
 *
 * @author legendjw
 */
public class ValidateVerificationCodeQuery {
    public ValidateVerificationCodeQuery() {
    }

    public ValidateVerificationCodeQuery(String scene, String messageType, String address, String code) {
        this.scene = scene;
        this.messageType = messageType;
        this.address = address;
        this.code = code;
    }

    /**
     * 场景
     */
    @NotBlank(message = "请输入场景")
    private String scene;

    /**
     * 消息类型 SMS: 短信, EMAIL: 邮箱
     */
    @NotBlank(message = "请输入消息类型")
    private String messageType;

    /**
     * 地址
     */
    @NotBlank(message = "请输入接收地址")
    private String address;

    /**
     * 验证码
     */
    @NotBlank(message = "请输入验证码")
    private String code;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}