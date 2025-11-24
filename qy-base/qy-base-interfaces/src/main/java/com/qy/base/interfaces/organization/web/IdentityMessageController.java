package com.qy.base.interfaces.organization.web;

import com.qy.message.api.client.MessageClient;
import com.qy.message.api.command.SetAllMessageReadCommand;
import com.qy.message.api.command.SetMessageReadCommand;
import com.qy.message.api.dto.InternalMessageDTO;
import com.qy.message.api.dto.MessageCountDTO;
import com.qy.message.api.query.MessageQuery;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 认证用户消息
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/employee/messages")
public class IdentityMessageController {
    private OrganizationSessionContext sessionContext;
    private MessageClient messageClient;

    public IdentityMessageController(OrganizationSessionContext sessionContext, MessageClient messageClient) {
        this.sessionContext = sessionContext;
        this.messageClient = messageClient;
    }

    /**
     * 获取消息
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<List<InternalMessageDTO>> getMessages(MessageQuery query) {
        query.setContext(OrganizationSessionContext.contextId);
        query.setUserId(sessionContext.getUser().getId().toString());
        SimplePageImpl<InternalMessageDTO> page = messageClient.getMessages(query);
        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取消息数量
     *
     * @return
     */
    @GetMapping("/unread-count")
    public ResponseEntity<MessageCountDTO> getMessageCount() {
        MessageQuery query = new MessageQuery();
        query.setContext(OrganizationSessionContext.contextId);
        query.setUserId(sessionContext.getUser().getId().toString());
        query.setIsRead(0);
        return ResponseUtils.ok().body(messageClient.getMessageCount(query));
    }

    /**
     * 设置消息已读
     *
     * @return
     */
    @PostMapping("/{id}/set-message-read")
    public ResponseEntity<Object> setMessageRead(
            @PathVariable(value = "id") Long id
    ) {
        SetMessageReadCommand command = new SetMessageReadCommand();
        command.setContext(OrganizationSessionContext.contextId);
        command.setUserId(sessionContext.getUser().getId().toString());
        command.setMessageId(id);
        messageClient.setMessageRead(command);
        return ResponseUtils.ok("设置消息已读成功").build();
    }

    /**
     * 设置所有消息已读
     *
     * @return
     */
    @PostMapping("/set-all-message-read")
    public ResponseEntity<Object> setAllMessageRead() {
        SetAllMessageReadCommand command = new SetAllMessageReadCommand();
        command.setContext(OrganizationSessionContext.contextId);
        command.setUserId(sessionContext.getUser().getId().toString());

        messageClient.setAllMessageRead(command);

        return ResponseUtils.ok("设置所有消息已读成功").build();
    }
}