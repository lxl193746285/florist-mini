package com.qy.verification.app.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.verification.app.domain.aggregate.SentVerificationCode;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.Scene;
import com.qy.verification.app.domain.valueobject.SentId;
import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeDO;

/**
 * 验证码资源库
 *
 * @author legendjw
 */
public interface VerificationCodeRepository {
    /**
     * 查找一个已发送的验证码
     *
     * @param scene
     * @param receiver
     * @param code
     * @return
     */
    SentVerificationCode findSentVerificationCode(Scene scene, Receiver receiver, String code);

    /**
     * 保存一个已发送的验证码
     *
     * @param sentVerificationCode
     * @return
     */
    SentId saveSentVerificationCode(SentVerificationCode sentVerificationCode);

    IPage<VerificationCodeDO> verificationCodeDOList(IPage<VerificationCodeDO> iPage, String receiverAddress, String scene);
}