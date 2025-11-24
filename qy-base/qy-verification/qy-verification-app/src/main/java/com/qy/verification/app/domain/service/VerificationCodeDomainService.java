package com.qy.verification.app.domain.service;

import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.sender.SendResult;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.Scene;

/**
 * 验证码领域服务
 *
 * @author legendjw
 */
public interface VerificationCodeDomainService {
    /**
     * 生成指定长度的验证码
     *
     * @param scene
     * @param length
     * @return
     */
    VerificationCode generateVerificationCode(Scene scene, int length);

    /**
     * 给指定接收方发送一个指定的验证码
     *
     * @param receiver
     * @param verificationCode
     * @return
     */
    SendResult sendVerificationCode(Receiver receiver, VerificationCode verificationCode);
}
