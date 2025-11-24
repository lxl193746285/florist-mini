package com.qy.verification.app.domain.entity;

import com.qy.verification.app.domain.sender.SendResult;
import com.qy.verification.app.domain.valueobject.LogId;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码日志实体
 *
 * @author legendjw
 */
public class VerificationCodeLog implements Serializable {
    @Getter
    private LogId logId;
    @Getter
    private SendResult sendResult;
    @Getter
    private LocalDateTime createTime = LocalDateTime.now();

    public VerificationCodeLog(SendResult sendResult) {
        this.sendResult = sendResult;
    }

    public VerificationCodeLog(SendResult sendResult, LocalDateTime createTime) {
        this.sendResult = sendResult;
        this.createTime = createTime;
    }

    public VerificationCodeLog(LogId logId, SendResult sendResult, LocalDateTime createTime) {
        this.logId = logId;
        this.sendResult = sendResult;
        this.createTime = createTime;
    }
}