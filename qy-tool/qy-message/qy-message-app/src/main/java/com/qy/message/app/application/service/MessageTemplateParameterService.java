package com.qy.message.app.application.service;

import com.qy.message.app.application.dto.User;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateParameter;

import java.util.Map;

public interface MessageTemplateParameterService {
  /**
   * 获取消息模版参数
   * 
   * @param page
   * @param perPage
   * @param keyMode 取值类型
   * @param templateId 模版id
   * @param user
 * @param parameterType 
   * @return
   */
  ResultBean<MessageTemplateParameter> getMessageTemplateParameters(Integer page, Integer perPage,
                                                                    Byte keyMode, Integer templateId, User user, Byte parameterType);

  /**
   * 根据id获取模版消息参数
   * 
   * @param id
   * @return
   */
  MessageTemplateParameter getMessageTemplateParametersById(Integer id);

  /**
   * 创建消息模版参数
   * 
   * @param messageTemplateParameter
   * @return
   */
  MessageTemplateParameter createMessageTemplateParameter(
      MessageTemplateParameter messageTemplateParameter);

  /**
   * 修改消息模版参数
   * 
   * @param messageTemplateParameter
   * @return
   */
  MessageTemplateParameter updateMessageTemplateParameter(
      MessageTemplateParameter messageTemplateParameter);

  /**
   * 删除消息模版参数
   * 
   * @param id
   * @return
   */
  Integer deleteMessageTemplateParameter(Integer id);

  /**
   * 解析变量参数
   * 
   * @param templateId
   * @param map
   * @return
   */
  public Map<String, Object> parameterParse(Integer templateId, Map<String, Object> map);
}
