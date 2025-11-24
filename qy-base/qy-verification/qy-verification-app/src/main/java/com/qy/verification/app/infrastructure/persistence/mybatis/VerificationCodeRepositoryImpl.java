package com.qy.verification.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.verification.app.domain.aggregate.SentVerificationCode;
import com.qy.verification.app.domain.repository.VerificationCodeRepository;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.Scene;
import com.qy.verification.app.domain.valueobject.SentId;
import com.qy.verification.app.infrastructure.persistence.mybatis.converter.VerificationCodeConverter;
import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeDO;
import com.qy.verification.app.infrastructure.persistence.mybatis.mapper.VerificationCodeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

/**
 * 验证码资源库实现
 *
 * @author legendjw
 */
@Repository
public class VerificationCodeRepositoryImpl implements VerificationCodeRepository {
    private VerificationCodeMapper verificationCodeMapper;
    private VerificationCodeConverter verificationCodeConverter;

    public VerificationCodeRepositoryImpl(VerificationCodeMapper verificationCodeMapper, VerificationCodeConverter verificationCodeConverter) {
        this.verificationCodeMapper = verificationCodeMapper;
        this.verificationCodeConverter = verificationCodeConverter;
    }

    @Override
    public SentVerificationCode findSentVerificationCode(Scene scene, Receiver receiver, String code) {
        LambdaQueryWrapper<VerificationCodeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(VerificationCodeDO::getScene, scene.getId())
                .eq(VerificationCodeDO::getReceiverMessageType, receiver.getMessageType().name())
                .eq(VerificationCodeDO::getReceiverAddress, receiver.getAddress())
                .eq(VerificationCodeDO::getCode, code)
                .eq(VerificationCodeDO::getIsUsed, 0)
                .orderByDesc(VerificationCodeDO::getCreateTime)
                .last("limit 1");
        VerificationCodeDO verificationCodeDO = verificationCodeMapper.selectOne(queryWrapper);
        if (verificationCodeDO == null) {
            return null;
        }

        return verificationCodeConverter.toVerificationCodeAggregate(verificationCodeDO);
    }

    @Override
    public SentId saveSentVerificationCode(SentVerificationCode sentVerificationCode) {
        VerificationCodeDO verificationCodeDO = verificationCodeConverter.toVerificationCodeDO(sentVerificationCode);
        if (verificationCodeDO.getId() == null) {
            verificationCodeMapper.insert(verificationCodeDO);
        } else {
            verificationCodeMapper.updateById(verificationCodeDO);
        }
        return new SentId(verificationCodeDO.getId());
    }

    @Override
    public IPage<VerificationCodeDO> verificationCodeDOList(IPage<VerificationCodeDO> iPage, String receiverAddress, String scene) {
        LambdaQueryWrapper<VerificationCodeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(receiverAddress != null, VerificationCodeDO::getReceiverAddress, receiverAddress)
                .eq(scene != null, VerificationCodeDO::getScene, scene)
                .orderByDesc(VerificationCodeDO::getCreateTime);
        return verificationCodeMapper.selectPage(iPage, queryWrapper);
    }
}
