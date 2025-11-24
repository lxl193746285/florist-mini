package com.qy.verification.api.client;

import com.qy.verification.api.dto.SendEmailVerificationCodeCommand;
import com.qy.verification.api.dto.SendSmsVerificationCodeCommand;
import com.qy.verification.api.dto.ValidateCodeResultDTO;
import com.qy.verification.api.query.ValidateVerificationCodeQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 验证码远程调用服务
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-verification-code")
public interface VerificationCodeClient {
    /**
     * 验证指定验证码是否合法
     *
     * @param verificationCodeQuery
     * @return
     */
    @GetMapping(value = "/v4/api/verification/verification-codes/validations")
    ValidateCodeResultDTO validateVerificationCode(@SpringQueryMap ValidateVerificationCodeQuery verificationCodeQuery);
    /**
     * 验证指定验证码是否合法(验证码不失效)
     *
     * @param verificationCodeQuery
     * @return
     */
    @GetMapping(value = "/v4/api/verification/verification-codes/validations-no-used")
    ValidateCodeResultDTO validateVerificationCodeNoUsed(@SpringQueryMap ValidateVerificationCodeQuery verificationCodeQuery);

    /**
     * 发送短信验证码
     *
     * @param command
     */
    @PostMapping("/v4/api/verification/verification-codes/sms-send")
    void smsSend(
            @Valid @RequestBody SendSmsVerificationCodeCommand command
    );

    /**
     * 发送邮箱验证码
     *
     * @param command
     */
    @PostMapping("/v4/api/verification/verification-codes/email-send")
    void emailSend(
            @Valid @RequestBody SendEmailVerificationCodeCommand command
    );
}