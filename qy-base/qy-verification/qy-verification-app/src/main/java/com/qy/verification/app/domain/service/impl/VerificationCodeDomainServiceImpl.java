package com.qy.verification.app.domain.service.impl;

import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.sender.SendResult;
import com.qy.verification.app.domain.sender.Sender;
import com.qy.verification.app.domain.sender.SenderFactory;
import com.qy.verification.app.domain.service.VerificationCodeDomainService;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.Scene;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodeDomainServiceImpl implements VerificationCodeDomainService {
    private SenderFactory senderFactory;

    public VerificationCodeDomainServiceImpl(SenderFactory senderFactory) {
        this.senderFactory = senderFactory;
    }

    @Override
    public VerificationCode generateVerificationCode(Scene scene, int length) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int num = r.nextInt(9);
            sb.append(num);
        }
        return new VerificationCode(scene, sb.toString());
    }

    @Override
    public SendResult sendVerificationCode(Receiver receiver, VerificationCode verificationCode) {
        Sender sender = senderFactory.createSender(receiver.getMessageType());
        return sender.send(receiver, verificationCode);
    }
}