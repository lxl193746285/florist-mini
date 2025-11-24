package com.qy.tool.interfaces.message.api;

import com.qy.message.app.application.command.MessageLogCommand;
import com.qy.message.app.application.command.SetAllMessageReadCommand;
import com.qy.message.app.application.command.SetMessageReadCommand;
import com.qy.message.app.application.dto.InternalMessageDTO;
import com.qy.message.app.application.dto.MessageCountDTO;
import com.qy.message.app.application.dto.MessageLogDTO;
import com.qy.message.app.application.query.MessageQuery;
import com.qy.message.app.application.service.MessageLogService;
import com.qy.message.app.application.service.MessageService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 消息
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/message/messages")
public class MessageApiController {
    private MessageService messageService;

    @Autowired
    private MessageLogService messageLogService;
    private static final Logger logger = LoggerFactory.getLogger(MessageApiController.class);
    public MessageApiController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 获取消息
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<SimplePageImpl<InternalMessageDTO>> getMessages(MessageQuery query) {
        Page<InternalMessageDTO> page = messageService.getMessages(query);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
    }

    /**
     * 获取消息数量
     *
     * @param query
     * @return
     */
    @GetMapping("/count")
    public ResponseEntity<MessageCountDTO> getMessageCount(MessageQuery query) {
        return ResponseUtils.ok().body(messageService.getMessageCount(query));
    }

    /**
     * 设置消息已读
     *
     * @param command
     * @return
     */
    @PostMapping("/set-message-read")
    public ResponseEntity<Object> setMessageRead(
            @Valid @RequestBody SetMessageReadCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        messageService.setMessageRead(command);

        return ResponseUtils.ok("设置消息已读成功").build();
    }

    /**
     * 设置所有消息已读
     *
     * @param command
     * @return
     */
    @PostMapping("/set-all-message-read")
    public ResponseEntity<Object> setAllMessageRead(
            @Valid @RequestBody SetAllMessageReadCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        messageService.setAllMessageRead(command);

        return ResponseUtils.ok("设置所有消息已读成功").build();
    }

    /**
     * 获取消息日志记录列表
     * @param templateId
     * @param messageType
     * @param receiveUserId
     * @param srcId
     * @param srcRowId
     * @return
     */
    @GetMapping("/messageLogs")
    public List<MessageLogDTO> getMessageLogs(
            @RequestParam(value = "template_id",required = false) Long templateId,
            @RequestParam(value = "message_type",required = false) Integer messageType,
            @RequestParam(value = "receive_user_id",required = false) Long receiveUserId,
            @RequestParam(value = "src_id",required = false) String srcId,
            @RequestParam(value = "src_row_id",required = false) Long srcRowId
            ) {
        MessageLogCommand messageLogCommand = new MessageLogCommand();
        messageLogCommand.setTemplateId(templateId);
        messageLogCommand.setMessageType(messageType);
        messageLogCommand.setReceiveUserId(receiveUserId);
        messageLogCommand.setSrcId(srcId);
        messageLogCommand.setSrcRowId(srcRowId);
        List<MessageLogDTO> list = messageLogService.getMessageLogs(messageLogCommand);
        return list;
    }
}