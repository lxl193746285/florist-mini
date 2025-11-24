package com.qy.verification.app.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.verification.app.application.command.SendEmailVerificationCodeCommand;
import com.qy.verification.app.application.command.SendSmsVerificationCodeCommand;
import com.qy.verification.app.application.dto.ImageValidateCodeDTO;
import com.qy.verification.app.application.dto.ValidateCodeResultDTO;
import com.qy.verification.app.application.dto.VerificationCodeDTO;
import com.qy.verification.app.application.query.ValidateVerificationCodeQuery;
import com.qy.verification.app.domain.sender.SendResult;

import java.io.IOException;

/**
 * 验证码应用服务
 *
 * @author legendjw
 */
public interface VerificationCodeService {
    /**
     * 发送短信验证码
     *
     * @param command
     * @return
     */
    SendResult sendVerificationCode(SendSmsVerificationCodeCommand command);

    /**
     * 发送邮箱验证码
     *
     * @param command
     * @return
     */
    SendResult sendVerificationCode(SendEmailVerificationCodeCommand command);
    /**
     * 发送短信验证码(需验证图形验证码)
     *
     * @param command
     * @return
     */
    SendResult sendVerificationCodeWithImageValidate(SendSmsVerificationCodeCommand command);

    /**
     * 验证一个验证码
     *
     * @param query
     * @return
     */
    ValidateCodeResultDTO validateVerificationCode(ValidateVerificationCodeQuery query);
    /**
     * 验证一个验证码(验证码不失效)
     *
     * @param query
     * @return
     */
    ValidateCodeResultDTO validateVerificationCodeNoUsed(ValidateVerificationCodeQuery query);

    ImageValidateCodeDTO getImageValidateCode() throws IOException;

    void validateImageCode(String tmpAccessToken, String code);

    IPage<VerificationCodeDTO> verificationCodeDOList(IPage<VerificationCodeDTO> iPage, String receiverAddress, String scene);
}
