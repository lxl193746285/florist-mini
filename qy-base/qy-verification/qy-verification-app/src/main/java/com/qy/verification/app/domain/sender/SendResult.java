package com.qy.verification.app.domain.sender;

import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.valueobject.Receiver;
import lombok.Getter;

import java.io.Serializable;

/**
 * 发送结果
 *
 * @author legendjw
 */
public class SendResult implements Serializable {
    @Getter
    private boolean isSuccess;
    @Getter
    private String errorMessage;
    @Getter
    private Receiver receiver;
    @Getter
    private VerificationCode verificationCode;

    public SendResult(boolean isSuccess, Receiver receiver, VerificationCode verificationCode) {
        this.isSuccess = isSuccess;
        this.receiver = receiver;
        this.verificationCode = verificationCode;
    }

    public SendResult(boolean isSuccess, String errorMessage, Receiver receiver, VerificationCode verificationCode) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
        this.receiver = receiver;
        this.verificationCode = verificationCode;
    }
}