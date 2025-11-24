package com.qy.message.app.infrastructure.persistence;

import com.qy.message.app.application.query.MessageQuery;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageUserDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageWithUserDO;
import com.qy.rest.pagination.Page;

/**
 * 站内信资源库
 *
 * @author legendjw
 */
public interface InternalMessageRepository {
    /**
     * 根据查询获取站内信
     *
     * @param query
     * @return
     */
    Page<MessageWithUserDO> findInternalMessageByQuery(MessageQuery query);

    /**
     * 根据查询条件查询消息数量
     *
     * @param query
     * @return
     */
    Integer findInternalMessageCountByQuery(MessageQuery query);

    /**
     * 查找站内信
     *
     * @param messageId
     * @param context
     * @param contextId
     * @param userId
     * @return
     */
    MessageUserDO findInternalMessageUser(Long messageId, String context, String contextId, String userId);

    /**
     * 保存一条站内信
     *
     * @param messageDO
     * @return
     */
    Long saveInternalMessage(MessageDO messageDO);

    /**
     * 保存站内信用户
     *
     * @param messageUserDO
     * @return
     */
    Long saveInternalMessageUser(MessageUserDO messageUserDO);

    /**
     * 批量设置所有消息已读
     *
     * @param context
     * @param contextId
     * @param userId
     */
    void setInternalMessageAllRead(String context, String contextId, String userId);
}
