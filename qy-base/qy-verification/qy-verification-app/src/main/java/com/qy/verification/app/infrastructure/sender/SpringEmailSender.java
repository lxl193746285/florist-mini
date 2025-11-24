package com.qy.verification.app.infrastructure.sender;

import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.sender.EmailSender;
import com.qy.verification.app.domain.sender.SendResult;
import com.qy.verification.app.domain.valueobject.Receiver;
import org.springframework.stereotype.Component;

/**
 * 邮箱验证码发送器
 *
 * @author legendjw
 */
@Component
public class SpringEmailSender implements EmailSender {
    @Override
    public SendResult send(Receiver receiver, VerificationCode verificationCode) {
        return new SendResult(true, receiver, verificationCode);
    }
}
