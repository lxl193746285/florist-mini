package com.qy.verification.app.infrastructure.persistence.mybatis;

import com.qy.verification.app.domain.entity.VerificationCodeLog;
import com.qy.verification.app.domain.repository.VerificationCodeLogRepository;
import com.qy.verification.app.domain.valueobject.LogId;
import com.qy.verification.app.infrastructure.persistence.mybatis.converter.VerificationCodeLogConverter;
import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeLogDO;
import com.qy.verification.app.infrastructure.persistence.mybatis.mapper.VerificationCodeLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 验证码日志资源库实现
 *
 * @author legendjw
 */
@Repository
public class VerificationCodeLogRepositoryImpl implements VerificationCodeLogRepository {
    private VerificationCodeLogMapper verificationCodeLogMapper;
    private VerificationCodeLogConverter verificationCodeLogConverter;

    public VerificationCodeLogRepositoryImpl(VerificationCodeLogMapper verificationCodeLogMapper, VerificationCodeLogConverter verificationCodeLogConverter) {
        this.verificationCodeLogMapper = verificationCodeLogMapper;
        this.verificationCodeLogConverter = verificationCodeLogConverter;
    }

    @Override
    public LogId saveVerificationCodeLog(VerificationCodeLog verificationCodeLog) {
        VerificationCodeLogDO verificationCodeLogDO = verificationCodeLogConverter.toVerificationCodeLogDO(verificationCodeLog);
        if (verificationCodeLogDO.getId() == null) {
            verificationCodeLogMapper.insert(verificationCodeLogDO);
        }
        else {
            verificationCodeLogMapper.updateById(verificationCodeLogDO);
        }
        return new LogId(verificationCodeLogDO.getId());
    }
}
