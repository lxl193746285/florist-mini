package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.assembler.MessageAssembler;
import com.qy.message.app.application.command.SetAllMessageReadCommand;
import com.qy.message.app.application.command.SetMessageReadCommand;
import com.qy.message.app.application.dto.InternalMessageDTO;
import com.qy.message.app.application.dto.MessageCountDTO;
import com.qy.message.app.application.query.MessageQuery;
import com.qy.message.app.application.service.MessageService;
import com.qy.message.app.infrastructure.persistence.InternalMessageRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageUserDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageWithUserDO;
import com.qy.rest.pagination.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 消息服务实现
 *
 * @author legendjw
 */
@Service
public class MessageServiceImpl implements MessageService {
    private InternalMessageRepository internalMessageRepository;
    private MessageAssembler messageAssembler;

    public MessageServiceImpl(InternalMessageRepository internalMessageRepository, MessageAssembler messageAssembler) {
        this.internalMessageRepository = internalMessageRepository;
        this.messageAssembler = messageAssembler;
    }

    @Override
    public Page<InternalMessageDTO> getMessages(MessageQuery query) {
        Page<MessageWithUserDO> messageWithUserDOPage = internalMessageRepository.findInternalMessageByQuery(query);
        return messageWithUserDOPage.map(memberDO -> messageAssembler.toDTO(memberDO));
    }

    @Override
    public MessageCountDTO getMessageCount(MessageQuery query) {
        MessageCountDTO messageCountDTO = new MessageCountDTO();
        messageCountDTO.setCount(internalMessageRepository.findInternalMessageCountByQuery(query));
        return messageCountDTO;
    }

    @Override
    public void setMessageRead(SetMessageReadCommand command) {
        MessageUserDO messageUserDO = internalMessageRepository
                .findInternalMessageUser(command.getMessageId(), command.getContext(), command.getContextId(), command.getUserId());
        if (messageUserDO != null && messageUserDO.getIsRead().intValue() == 0) {
            messageUserDO.setIsRead((byte) 1);
            messageUserDO.setReadTime(LocalDateTime.now());
            internalMessageRepository.saveInternalMessageUser(messageUserDO);
        }
    }

    @Override
    public void setAllMessageRead(SetAllMessageReadCommand command) {
        internalMessageRepository.setInternalMessageAllRead(command.getContext(), command.getContextId(), command.getUserId());
    }
}
