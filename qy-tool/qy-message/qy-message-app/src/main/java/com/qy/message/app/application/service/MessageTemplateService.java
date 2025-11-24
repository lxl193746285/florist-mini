package com.qy.message.app.application.service;

import com.qy.message.app.application.dto.MessageTemplateVo;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplate;

import java.util.List;
import java.util.Map;

public interface MessageTemplateService {

    ResultBean<MessageTemplateVo> selectTemplateList(Integer page, Integer perPage, Map<String,Object> params);

    MessageTemplateVo selectOne(Integer id);

    MessageTemplateVo createMessageTemplateVo(MessageTemplateVo messageTemplateVo);

    MessageTemplateVo updateMessageTemplateVo(MessageTemplateVo messageTemplateVo);

    int deleteMessageTemplateVo(Integer id);

    List<MessageTemplateVo> selectTemplates();

    MessageTemplate getMessageTemplateByModuleFunction(String module, String function, String weixinAppId);

    String getParsedTitle(MessageTemplate messageTemplate, Map<String, Object> params);

    String getParsedContent(MessageTemplate messageTemplate, Map<String, Object> params);
}
