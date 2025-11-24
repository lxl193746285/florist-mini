package com.qy.tool.interfaces.message.api;

import com.qy.message.app.application.command.SendTemplateMessageCommand;
import com.qy.message.app.application.dto.UserMessageWxDto;
import com.qy.message.app.application.service.TemplateMessageService;
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
 * 模版消息
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/message/template-messages")
public class TemplateMessageApiController {
    private TemplateMessageService templateMessageService;

    public TemplateMessageApiController(TemplateMessageService templateMessageService) {
        this.templateMessageService = templateMessageService;
    }

    /**
     * 发送模版消息
     *
     * @param command
     * @return
     */
    @PostMapping("/send")
    public ResponseEntity<Object> sendTemplateMessage(
            @Valid @RequestBody SendTemplateMessageCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        if(!CollectionUtils.isEmpty(command.getUserMessageWxDtos())){
            for (UserMessageWxDto userMessageWxDto : command.getUserMessageWxDtos()) {
                SendTemplateMessageCommand sendTemplateMessageCommand = new SendTemplateMessageCommand();
                BeanUtils.copyProperties(command,sendTemplateMessageCommand);
                sendTemplateMessageCommand.setUserId(userMessageWxDto.getUserId().toString());
                sendTemplateMessageCommand.setOpenId(userMessageWxDto.getOpenid());
                templateMessageService.sendTemplateMessage(sendTemplateMessageCommand);
            }
        }else {
            templateMessageService.sendTemplateMessage(command);
        }
        return ResponseUtils.ok("发送模版消息成功").build();
    }
}

