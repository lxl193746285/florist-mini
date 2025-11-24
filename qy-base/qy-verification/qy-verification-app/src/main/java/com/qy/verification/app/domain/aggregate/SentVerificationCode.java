package com.qy.verification.app.domain.aggregate;

import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.SentId;

import java.io.Serializable;

/**
 * 已发送的验证码
 *
 * @author legendjw
 */
public class SentVerificationCode implements Serializable {
    private SentId sentId;
    private Receiver receiver;
    private VerificationCode verificationCode;

    public SentVerificationCode(Receiver receiver, VerificationCode verificationCode) {
        this.receiver = receiver;
        this.verificationCode = verificationCode;
    }

    public SentVerificationCode(SentId sentId, Receiver receiver, VerificationCode verificationCode) {
        this.sentId = sentId;
        this.receiver = receiver;
        this.verificationCode = verificationCode;
    }

    public SentId getSentId() {
        return sentId;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public VerificationCode getVerificationCode() {
        return verificationCode;
    }
}