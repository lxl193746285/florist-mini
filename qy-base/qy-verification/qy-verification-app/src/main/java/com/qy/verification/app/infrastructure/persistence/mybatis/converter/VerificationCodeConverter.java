package com.qy.verification.app.infrastructure.persistence.mybatis.converter;

import com.qy.verification.app.domain.aggregate.SentVerificationCode;
import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.enums.MessageType;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.Scene;
import com.qy.verification.app.domain.valueobject.SentId;
import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeDO;
import org.mapstruct.Mapper;

/**
 * 验证码转换器
 *
 * @author legendjw
 */
@Mapper
public interface VerificationCodeConverter {
    /**
     * 发送验证码转为DO
     *
     * @param sentVerificationCode
     * @return
     */
    default VerificationCodeDO toVerificationCodeDO(SentVerificationCode sentVerificationCode) {
        if (sentVerificationCode == null) {
            return null;
        }
        VerificationCodeDO verificationCodeDO = new VerificationCodeDO();
        if (sentVerificationCode.getSentId() != null) {
            verificationCodeDO.setId(sentVerificationCode.getSentId().getId());
        }
        Receiver receiver = sentVerificationCode.getReceiver();
        VerificationCode verificationCode = sentVerificationCode.getVerificationCode();
        verificationCodeDO.setScene(verificationCode.getScene().getId());
        verificationCodeDO.setReceiverMessageType(receiver.getMessageType().name());
        verificationCodeDO.setReceiverAddress(receiver.getAddress());
        verificationCodeDO.setCode(verificationCode.getCode());
        verificationCodeDO.setValidDuration(verificationCode.getValidDuration());
        verificationCodeDO.setCreateTime(verificationCode.getCreateTime());
        verificationCodeDO.setExpiredTime(verificationCode.getExpiredTime());
        verificationCodeDO.setIsUsed(verificationCode.isUsed() ? (byte) 1 : (byte) 0);
        verificationCodeDO.setUseTime(verificationCode.getUseTime());
        return verificationCodeDO;
    }

    /**
     * 验证码DO转为验证码聚合
     *
     * @param verificationCodeDO
     * @return
     */
    default SentVerificationCode toVerificationCodeAggregate(VerificationCodeDO verificationCodeDO) {
        if (verificationCodeDO == null) {
            return null;
        }
        Receiver receiver = new Receiver(
                MessageType.valueOf(verificationCodeDO.getReceiverMessageType()),
                verificationCodeDO.getReceiverAddress()
        );
        VerificationCode verificationCode = new VerificationCode(
                new Scene(verificationCodeDO.getScene()),
                verificationCodeDO.getCode(),
                verificationCodeDO.getValidDuration(),
                verificationCodeDO.getCreateTime(),
                verificationCodeDO.getIsUsed().intValue() == 1 ? true : false,
                verificationCodeDO.getUseTime()
        );
        return new SentVerificationCode(new SentId(verificationCodeDO.getId()), receiver, verificationCode);
    }
}
