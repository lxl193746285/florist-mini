package com.qy.verification.app.domain.sender;

import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.valueobject.Receiver;

/**
 * 验证码发送器
 * 主要负责发送验证码
 *
 * @author legendjw
 */
public interface Sender {
    /**
     * 给指定接收方发送验证码
     *
     * @param receiver
     * @param verificationCode
     * @return
     */
    SendResult send(Receiver receiver, VerificationCode verificationCode);
}