package com.qy.verification.app.infrastructure.persistence.mybatis.converter;

import com.qy.verification.app.domain.entity.VerificationCodeLog;
import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 验证码日志转化器
 *
 * @author legendjw
 */
@Mapper(uses = BooleanAsByteMapper.class)
public interface VerificationCodeLogConverter {
    @Mappings({
            @Mapping(source = "logId.id", target = "id"),
            @Mapping(source = "sendResult.receiver.messageType", target = "receiverMessageType"),
            @Mapping(source = "sendResult.receiver.address", target = "receiverAddress"),
            @Mapping(source = "sendResult.verificationCode.scene.id", target = "scene"),
            @Mapping(source = "sendResult.verificationCode.code", target = "code"),
            @Mapping(source = "sendResult.success", target = "result"),
            @Mapping(source = "sendResult.errorMessage", target = "error"),
    })
    VerificationCodeLogDO toVerificationCodeLogDO(VerificationCodeLog verificationCodeLog);
}