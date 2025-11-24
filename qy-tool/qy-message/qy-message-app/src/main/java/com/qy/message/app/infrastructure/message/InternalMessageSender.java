package com.qy.message.app.infrastructure.message;

import com.qy.message.app.application.dto.SendInternalMessageDTO;
import com.qy.message.app.infrastructure.persistence.InternalMessageRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageUserDO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站内信发送器
 *
 * @author legendjw
 */
@Component
public class InternalMessageSender {
    private InternalMessageRepository internalMessageRepository;

    public InternalMessageSender(InternalMessageRepository internalMessageRepository) {
        this.internalMessageRepository = internalMessageRepository;
    }

    /**
     * 发送消息
     *
     * @param message
     */
    @Transactional
    public void sendMessage(SendInternalMessageDTO message) {
        MessageDO messageDO = new MessageDO();
        messageDO.setTitle(message.getTitle());
        messageDO.setContent(message.getContent());
        messageDO.setPrimaryModuleId(message.getPrimaryModuleId());
        messageDO.setPrimaryModuleName(message.getPrimaryModuleName());
        messageDO.setPrimaryDataId(message.getPrimaryDataId());
        messageDO.setSecondaryModuleId(message.getSecondaryModuleId());
        messageDO.setSecondaryModuleName(message.getSecondaryModuleName());
        messageDO.setSecondaryDataId(message.getSecondaryDataId());
        messageDO.setLink(message.getLink());
        internalMessageRepository.saveInternalMessage(messageDO);

        MessageUserDO messageUserDO = new MessageUserDO();
        messageUserDO.setMessageId(messageDO.getId());
        messageUserDO.setContext(message.getContext());
        messageUserDO.setContextName(message.getContextName());
        messageUserDO.setContextId(message.getContextId());
        messageUserDO.setUserId(message.getUserId());
        internalMessageRepository.saveInternalMessageUser(messageUserDO);
    }
}