package com.qy.message.app.application.service;

import com.qy.message.app.application.command.SetAllMessageReadCommand;
import com.qy.message.app.application.command.SetMessageReadCommand;
import com.qy.message.app.application.dto.InternalMessageDTO;
import com.qy.message.app.application.dto.MessageCountDTO;
import com.qy.message.app.application.query.MessageQuery;
import com.qy.rest.pagination.Page;

/**
 * 消息服务
 *
 * @author legendjw
 */
public interface MessageService {
    /**
     * 获取消息
     *
     * @param query
     * @return
     */
    Page<InternalMessageDTO> getMessages(MessageQuery query);

    /**
     * 查询消息数量
     *
     * @param query
     * @return
     */
    MessageCountDTO getMessageCount(MessageQuery query);

    /**
     * 设置消息已读
     *
     * @param command
     */
    void setMessageRead(SetMessageReadCommand command);

    /**
     * 设置所有消息已读
     *
     * @param command
     */
    void setAllMessageRead(SetAllMessageReadCommand command);
}
