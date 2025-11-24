package com.qy.tool.interfaces.message.api;

import com.qy.message.app.application.command.SendMessageSMSCommand;
import com.qy.message.app.application.service.MessageSMSService;
import com.qy.message.app.domain.sender.SendSMSResult;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 短信消息
 *
 * @author lxl
 * @since 2025-03-28
 */
@RestController
@RequestMapping("/v4/api/message/sms")
public class MessageSMSApiController {
    private MessageSMSService messageSMSService;

    public MessageSMSApiController(MessageSMSService messageSMSService) {
        this.messageSMSService = messageSMSService;
    }

    /**
     * 发送短信验证码
     *
     * @param command
     */
    @PostMapping("/sms-send")
    public ResponseEntity<SendSMSResult> smsSend(
            @Valid @RequestBody SendMessageSMSCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        SendSMSResult sendResult = messageSMSService.sendSMS(command);
        if (sendResult.isSuccess()) {
            return ResponseUtils.ok("短信发送成功").body(sendResult);
        } else {
            return ResponseUtils.internalServerError(sendResult.getErrorMessage()).body(sendResult);
        }
    }
}

