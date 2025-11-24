package com.qy.message.api.client;

import com.qy.message.api.command.SendMessageSMSCommand;

import com.qy.message.api.dto.SendSMSResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 短信客户端
 *
 * @author lxl
 */
@FeignClient(name = "qy-tool", contextId = "arkcsd-message-sms")
public interface SMSClient {
    /**
     * 发送短信验证码
     *
     * @param command
     */
    @PostMapping("/v4/api/message/sms/sms-send")
    SendSMSResult smsSend(
            @Valid @RequestBody SendMessageSMSCommand command
    );

}