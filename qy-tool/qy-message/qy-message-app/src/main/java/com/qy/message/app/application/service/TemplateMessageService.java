package com.qy.message.app.application.service;

import com.qy.message.app.application.command.SendTemplateMessageCommand;

/**
 * 模版消息服务
 *
 * @author legendjw
 */
public interface TemplateMessageService {
    /**
     * 发送模版消息
     *
     * @param command
     */
    void sendTemplateMessage(SendTemplateMessageCommand command);
}
