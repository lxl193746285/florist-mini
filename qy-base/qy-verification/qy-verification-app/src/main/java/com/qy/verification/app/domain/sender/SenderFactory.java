package com.qy.verification.app.domain.sender;

import com.qy.verification.app.domain.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送器工厂
 *
 * @author legendjw
 */
@Component
public class SenderFactory {
    @Autowired
    private SmsSender smsSender;
    @Autowired
    private EmailSender emailSender;

    /**
     * 根据消息类型创建发送器
     *
     * @param messageType
     * @return
     */
    public Sender createSender(MessageType messageType) {
        if (messageType.equals(MessageType.SMS)) {
            return smsSender;
        }
        else if (messageType.equals(MessageType.EMAIL)) {
            return emailSender;
        }
        else {
            throw new RuntimeException("不支持的消息发送类型");
        }
    }
}
