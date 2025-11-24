package com.qy.verification.app.domain.repository;

import com.qy.verification.app.domain.entity.VerificationCodeLog;
import com.qy.verification.app.domain.valueobject.LogId;

/**
 * 验证码日志资源库
 *
 * @author legendjw
 */
public interface VerificationCodeLogRepository {
    /**
     * 保存发送验证码日志
     *
     * @param verificationCodeLog
     * @return
     */
    LogId saveVerificationCodeLog(VerificationCodeLog verificationCodeLog);
}