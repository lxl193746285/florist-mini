package com.qy.verification.app.domain.valueobject;

import com.qy.verification.app.domain.enums.MessageType;

import java.io.Serializable;

/**
 * 接收方值对象
 *
 * @author legendjw
 */
public class Receiver implements Serializable {
    /**
     * 消息类型
     */
    private MessageType messageType;
    /**
     * 接收方地址
     */
    private String address;

    public Receiver(MessageType messageType, String address) {
        this.messageType = messageType;
        this.address = address;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getAddress() {
        return address;
    }
}
