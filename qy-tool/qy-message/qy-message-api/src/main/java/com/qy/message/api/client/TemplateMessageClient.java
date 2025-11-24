package com.qy.message.api.client;

import com.qy.message.api.command.SendTemplateMessageCommand;
import com.qy.message.api.dto.MessageLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 模版消息客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-tool", contextId = "arkcsd-message-template-message")
public interface TemplateMessageClient {
    /**
     * 发送模版消息
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/message/template-messages/send")
    void sendTemplateMessage(
            @Valid @RequestBody SendTemplateMessageCommand command
    );

    /**
     * 获取推送日志消息
     *
     * @return
     */
    @GetMapping("/v4/api/message/messages/messageLogs")
    List<MessageLogDTO> getMessageLogs(
            @RequestParam(value = "template_id",required = false) Long templateId,
            @RequestParam(value = "message_type",required = false) Integer messageType,
            @RequestParam(value = "receive_user_id",required = false) Long receiveUserId,
            @RequestParam(value = "src_id",required = false) String srcId,
            @RequestParam(value = "src_row_id",required = false) Long srcRowId
    );
}