package com.qy.base.interfaces.verification.api;

import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.verification.app.application.command.SendEmailVerificationCodeCommand;
import com.qy.verification.app.application.command.SendSmsVerificationCodeCommand;
import com.qy.verification.app.application.dto.ValidateCodeResultDTO;
import com.qy.verification.app.application.query.ValidateVerificationCodeQuery;
import com.qy.verification.app.application.service.VerificationCodeService;
import com.qy.verification.app.domain.sender.SendResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 验证码内部服务接口
 */
@RestController
@RequestMapping(value = "/v4/api/verification/verification-codes")
public class VerificationCodeApiController {
    private VerificationCodeService verificationCodeService;

    public VerificationCodeApiController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * 验证一个验证码
     *
     * @param query
     */
    @GetMapping("/validations")
    public ResponseEntity<ValidateCodeResultDTO> validateVerificationCode(
            ValidateVerificationCodeQuery query
    ) {
        ValidateCodeResultDTO validateCodeResultDTO = verificationCodeService.validateVerificationCode(query);
        return ResponseUtils.ok().body(validateCodeResultDTO);
    }

    /**
     * 验证一个验证码(验证码不失效)
     *
     * @param query
     */
    @GetMapping("/validations-no-used")
    public ResponseEntity<ValidateCodeResultDTO> validateVerificationCodeNoUsed(
            ValidateVerificationCodeQuery query
    ) {
        ValidateCodeResultDTO validateCodeResultDTO = verificationCodeService.validateVerificationCodeNoUsed(query);
        return ResponseUtils.ok().body(validateCodeResultDTO);
    }
    /**
     * 发送短信验证码
     *
     * @param command
     */
    @PostMapping("/sms-send")
    public ResponseEntity smsSend(
            @Valid @RequestBody SendSmsVerificationCodeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        SendResult sendResult = verificationCodeService.sendVerificationCode(command);
        if (sendResult.isSuccess()) {
            return ResponseUtils.ok("验证码发送成功").build();
        }
        else {
            return ResponseUtils.internalServerError(sendResult.getErrorMessage()).build();
        }
    }

    /**
     * 发送邮箱验证码
     *
     * @param command
     */
    @PostMapping("/email-send")
    public ResponseEntity emailSend(
            @Valid @RequestBody SendEmailVerificationCodeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        SendResult sendResult = verificationCodeService.sendVerificationCode(command);
        if (sendResult.isSuccess()) {
            return ResponseUtils.ok("验证码发送成功").build();
        }
        else {
            return ResponseUtils.internalServerError(sendResult.getErrorMessage()).build();
        }
    }
}