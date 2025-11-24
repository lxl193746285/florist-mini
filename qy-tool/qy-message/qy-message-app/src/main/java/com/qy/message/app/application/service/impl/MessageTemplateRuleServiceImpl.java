package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.dto.User;
import com.qy.message.app.application.enums.MessageTemplateRuleAction;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateRuleService;
import com.qy.message.app.application.service.TimingMessageService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRule;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRuleExample;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageTemplateRuleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageTemplateRuleServiceImpl implements MessageTemplateRuleService {
    @Autowired
    private MessageTemplateRuleMapper messageTemplateRuleMapper;
    private String prefix = "backend/backend/messageTemplateRule/";
    private String is_view_auth = prefix + "view";
    private String is_edit_auth = prefix + "update";
    private String is_delete_auth = prefix + "delete";
    private String is_add_auth = prefix + "create";
    @Autowired
    private TimingMessageService timingMessageService;
    //@Autowired
    //private QueueService queueService;

    @Override
    public ResultBean<MessageTemplateRule> getMessageTemplateRules(Integer page, Integer perPage,
                                                                   Integer templateId, User user) {
        ResultBean<MessageTemplateRule> result = new ResultBean<MessageTemplateRule>();
        PageHelper.startPage(page, perPage);
        MessageTemplateRuleExample messageTemplateRuleExample = new MessageTemplateRuleExample();
        MessageTemplateRuleExample.Criteria criteria = messageTemplateRuleExample.createCriteria();
        criteria.andTemplateIdEqualTo(templateId);
        List<MessageTemplateRule> messageTemplateRules =
                messageTemplateRuleMapper.selectByExample(messageTemplateRuleExample);
        if (CollectionUtils.isEmpty(messageTemplateRules)) {
            return result;
        }
        PageInfo<MessageTemplateRule> pageInfo = new PageInfo<>(messageTemplateRules);
        result.setTotalCount(pageInfo.getTotal());
        result.setPageCount((long) pageInfo.getPages());
        for (MessageTemplateRule messageTemplateRule : messageTemplateRules) {
            List<MessageTemplateRuleAction> actions =
                    getMessageTemplateRuleActions(messageTemplateRule, user);
            messageTemplateRule.setMessageTemplateParameterActions(actions);
        }
        result.setData(messageTemplateRules);
        return result;
    }

    private List<MessageTemplateRuleAction> getMessageTemplateRuleActions(
            MessageTemplateRule messageTemplateRule, User user) {
        List<MessageTemplateRuleAction> actions = new ArrayList<MessageTemplateRuleAction>();
        actions.add(MessageTemplateRuleAction.VIEW);
        actions.add(MessageTemplateRuleAction.ADD);
        actions.add(MessageTemplateRuleAction.EDIT);
        actions.add(MessageTemplateRuleAction.DELETE);
        Collections.sort(actions, (a, b) -> b.getSort().compareTo(a.getSort()));
        Collections.reverse(actions);
        return actions;
    }

    @Override
    public MessageTemplateRule getMessageTemplateRulesById(Integer id) {
        return messageTemplateRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageTemplateRule createMessageTemplateRule(MessageTemplateRule messageTemplateRule) {
        Integer result = messageTemplateRuleMapper.insertSelective(messageTemplateRule);
        timingMessageService.updateTodayTimingMessage();
        return result > 0 ? messageTemplateRule : null;
    }

    @Override
    public MessageTemplateRule updateMessageTemplateRule(MessageTemplateRule messageTemplateRule) {
        Integer result = messageTemplateRuleMapper.updateByPrimaryKeySelective(messageTemplateRule);
        timingMessageService.updateTodayTimingMessage();
        return result > 0 ? messageTemplateRule : null;
    }

    @Override
    public Integer deleteMessageTemplateRule(Integer id) {
        MessageTemplateRule messageTemplateRule = messageTemplateRuleMapper.selectByPrimaryKey(id);
        //queueService.cancelJob(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()) + "-"
        //        + messageTemplateRule.getTemplateId().toString() + "-" + id.toString());
        Integer result = messageTemplateRuleMapper.deleteByPrimaryKey(id);
        return result;
    }

    @Override
    public List<MessageTemplateRule> getAllMessageTemplateRules(Integer templateId) {
        MessageTemplateRuleExample messageTemplateRuleExample = new MessageTemplateRuleExample();
        MessageTemplateRuleExample.Criteria criteria = messageTemplateRuleExample.createCriteria();
        if (null != templateId) {
            criteria.andTemplateIdEqualTo(templateId);
        }
        List<MessageTemplateRule> messageTemplateRules =
                messageTemplateRuleMapper.selectByExample(messageTemplateRuleExample);
        return messageTemplateRules;
    }

}
