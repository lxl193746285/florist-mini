package com.qy.base.interfaces.verification.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.system.app.util.RestUtils;
import com.qy.verification.app.application.command.SendEmailVerificationCodeCommand;
import com.qy.verification.app.application.command.SendSmsVerificationCodeCommand;
import com.qy.verification.app.application.dto.ImageValidateCodeDTO;
import com.qy.verification.app.application.dto.VerificationCodeDTO;
import com.qy.verification.app.application.service.VerificationCodeService;
import com.qy.verification.app.domain.sender.SendResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 验证码web服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/verification/verification-codes")
public class VerificationCodeController {
    private VerificationCodeService verificationCodeService;

    public VerificationCodeController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @GetMapping
    public List<VerificationCodeDTO> verificationCodeDOList(
            @RequestParam(value = "receiver_address", required = false) String receiverAddress,
            @RequestParam(value = "scene", required = false) String scene,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "20") Integer perPage,
            HttpServletResponse response
    ) {
        IPage<VerificationCodeDTO> iPage = verificationCodeService.verificationCodeDOList(new Page<>(page, perPage), receiverAddress, scene);
        RestUtils.initResponseByPage(iPage, response);
        return iPage.getRecords();
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
        } else {
            return ResponseUtils.internalServerError(sendResult.getErrorMessage()).build();
        }
    }

    /**
     * 发送短信验证码（需图形验证码）
     *
     * @param command
     */
    @PostMapping("/sms-send-image-validate")
    public ResponseEntity smsSendWithImageValidate(
            @Valid @RequestBody SendSmsVerificationCodeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        SendResult sendResult = verificationCodeService.sendVerificationCodeWithImageValidate(command);
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
        } else {
            return ResponseUtils.internalServerError(sendResult.getErrorMessage()).build();
        }
    }

    @GetMapping("/image-validate-code")
    public ResponseEntity<ImageValidateCodeDTO> getImageValidateCode(){
        try {
            return ResponseUtils.ok().body(verificationCodeService.getImageValidateCode());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/image-validate-codes")
    public ImageValidateCodeDTO getImageValidateCodes(){
        try {
            return verificationCodeService.getImageValidateCode();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}