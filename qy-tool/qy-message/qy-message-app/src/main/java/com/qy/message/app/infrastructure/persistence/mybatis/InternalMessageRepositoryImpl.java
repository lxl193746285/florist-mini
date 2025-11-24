package com.qy.message.app.infrastructure.persistence.mybatis;

import com.qy.message.app.application.query.MessageQuery;
import com.qy.message.app.infrastructure.persistence.InternalMessageRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageUserDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageWithUserDO;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageMapper;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageUserMapper;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 站内信资源库实现
 *
 * @author legendjw
 */
@Repository
public class InternalMessageRepositoryImpl implements InternalMessageRepository {
    private MessageMapper messageMapper;
    private MessageUserMapper messageUserMapper;

    public InternalMessageRepositoryImpl(MessageMapper messageMapper, MessageUserMapper messageUserMapper) {
        this.messageMapper = messageMapper;
        this.messageUserMapper = messageUserMapper;
    }

    @Override
    public Page<MessageWithUserDO> findInternalMessageByQuery(MessageQuery query) {
        QueryWrapper<MessageWithUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m.is_deleted", 0).orderByDesc("m.create_time");

        if (StringUtils.isNotBlank(query.getContext())) {
            queryWrapper.eq("mu.context", query.getContext());
        }
        if (StringUtils.isNotBlank(query.getContextId())) {
            queryWrapper.eq("mu.context_id", query.getContextId());
        }
        if (StringUtils.isNotBlank(query.getUserId())) {
            queryWrapper.eq("mu.user_id", query.getUserId());
        }
        if (query.getIsRead() != null) {
            queryWrapper.eq("mu.is_read", query.getIsRead());
        }
        IPage<MessageWithUserDO> iPage = messageMapper.selectUserMessages(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public Integer findInternalMessageCountByQuery(MessageQuery query) {
        QueryWrapper<MessageWithUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m.is_deleted", 0).orderByDesc("m.create_time");

        if (StringUtils.isNotBlank(query.getContext())) {
            queryWrapper.eq("mu.context", query.getContext());
        }
        if (StringUtils.isNotBlank(query.getContextId())) {
            queryWrapper.eq("mu.context_id", query.getContextId());
        }
        if (StringUtils.isNotBlank(query.getUserId())) {
            queryWrapper.eq("mu.user_id", query.getUserId());
        }
        if (query.getIsRead() != null) {
            queryWrapper.eq("mu.is_read", query.getIsRead());
        }
        return messageMapper.selectUserMessageCount(queryWrapper);
    }

    @Override
    public MessageUserDO findInternalMessageUser(Long messageId, String context, String contextId, String userId) {
        LambdaQueryWrapper<MessageUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MessageUserDO::getMessageId, messageId)
                .eq(MessageUserDO::getContext, context)
                .eq(MessageUserDO::getContextId, contextId)
                .eq(MessageUserDO::getUserId, userId)
                .last("limit 1");
        return messageUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Long saveInternalMessage(MessageDO messageDO) {
        messageDO.setCreateTime(LocalDateTime.now());
        messageMapper.insert(messageDO);
        return messageDO.getId();
    }

    @Override
    public Long saveInternalMessageUser(MessageUserDO messageUserDO) {
        if (messageUserDO.getId() == null) {
            messageUserMapper.insert(messageUserDO);
        }
        else {
            messageUserMapper.updateById(messageUserDO);
        }
        return messageUserDO.getId();
    }

    @Override
    public void setInternalMessageAllRead(String context, String contextId, String userId) {
        LambdaQueryWrapper<MessageUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MessageUserDO::getContext, context)
                .eq(MessageUserDO::getContextId, contextId)
                .eq(MessageUserDO::getUserId, userId)
                .eq(MessageUserDO::getIsRead, 0);
        MessageUserDO messageUserDO = new MessageUserDO();
        messageUserDO.setIsRead((byte) 1);
        messageUserDO.setReadTime(LocalDateTime.now());
        messageUserMapper.update(messageUserDO, queryWrapper);
    }
}
