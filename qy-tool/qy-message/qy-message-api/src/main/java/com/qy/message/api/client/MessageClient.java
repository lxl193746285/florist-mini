package com.qy.message.api.client;

import com.qy.message.api.command.SetAllMessageReadCommand;
import com.qy.message.api.command.SetMessageReadCommand;
import com.qy.message.api.dto.InternalMessageDTO;
import com.qy.message.api.dto.MessageCountDTO;
import com.qy.message.api.dto.MessageLogDTO;
import com.qy.message.api.query.MessageQuery;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 消息客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-tool", contextId = "arkcsd-message-message")
public interface MessageClient {
    /**
     * 获取消息
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/message/messages")
    SimplePageImpl<InternalMessageDTO> getMessages(MessageQuery query);

    /**
     * 获取消息数量
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/message/messages/count")
    MessageCountDTO getMessageCount(MessageQuery query);

    /**
     * 设置消息已读
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/message/messages/set-message-read")
    void setMessageRead(
            @Valid @RequestBody SetMessageReadCommand command
    );

    /**
     * 设置所有消息已读
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/message/messages/set-all-message-read")
    void setAllMessageRead(
            @Valid @RequestBody SetAllMessageReadCommand command
    );

    /**
     * 获取消息日志信息
     * @param templateId
     * @param messageType
     * @param receiveUserId
     * @param srcId
     * @param srcRowId
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