package com.qy.tool.interfaces.message.web;

import com.qy.message.app.application.command.SendMessageSMSCommand;
import com.qy.message.app.application.service.MessageSMSService;
import com.qy.message.app.domain.sender.SendSMSResult;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信
 */
@RestController
@RequestMapping("/v4/message/sms")
public class MessageSMSController {
    @Autowired
    private MessageSMSService messageSMSService;

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
