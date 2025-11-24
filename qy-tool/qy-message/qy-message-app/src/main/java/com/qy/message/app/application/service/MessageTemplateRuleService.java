package com.qy.message.app.application.service;

import com.qy.message.app.application.dto.User;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRule;

import java.util.List;

public interface MessageTemplateRuleService {
  /**
   * 查询消息模版规则
   * 
   * @param page
   * @param perPage
   * @param templateId 模版id
   * @param user
   * @return
   */
  ResultBean<MessageTemplateRule> getMessageTemplateRules(Integer page, Integer perPage,
                                                          Integer templateId, User user);

  /**
   * 根据id获取消息模版规则
   * 
   * @param id
   * @return
   */
  MessageTemplateRule getMessageTemplateRulesById(Integer id);

  /**
   * 创建模版规则
   * 
   * @param messageTemplateRule
   * @return
   */
  MessageTemplateRule createMessageTemplateRule(MessageTemplateRule messageTemplateRule);

  /**
   * 修改模版规则
   * 
   * @param messageTemplateRule
   * @return
   */
  MessageTemplateRule updateMessageTemplateRule(MessageTemplateRule messageTemplateRule);

  /**
   * 删除模版规则
   * 
   * @param id
   * @return
   */
  Integer deleteMessageTemplateRule(Integer id);
  /**
   * 获取模版
   * @param templateId 模版id,不传查全部
   * @return
   */
  List<MessageTemplateRule> getAllMessageTemplateRules(Integer templateId);

}
